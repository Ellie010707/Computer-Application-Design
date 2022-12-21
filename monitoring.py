import docker
import time     
import json
import psutil
import platform
import os
# import cpuinfo


idx = 0

# 한 컨테이너의 docker stats 정보를 파싱하는 함수
# data : docker stats 명령어와 비슷한 결과물의 dictionary형의 데이터 (docker_stats.json 파일 형식 참고)
def docker_stats_convertor(data):
    container_id = data['id']
    container = client.containers.get(container_id)
    # IP 정보만 docker inspects에서 불러옴 (docker_inspection.json 파일 형식 참고)
    ip_add = container.attrs['NetworkSettings']['IPAddress']

    # 아래의 값들은 Docker Doc에서 가져온 공식을 활용하여 얻음.
    # 참고문헌 : https://docs.docker.com/engine/api/v1.41/#tag/Container/operation/ContainerExport
    json = {}
    json['IP'] = ip_add
    json['PID'] = data['pids_stats']['current']
    json['name'] = data['name']
    json['allocated_cpu_number'] = data['cpu_stats']['online_cpus']
    json['allocated_memory_size'] = data['memory_stats']['limit']

    cpu_delta = data['cpu_stats']['cpu_usage']['total_usage'] - data['precpu_stats']['cpu_usage']['total_usage']
    system_cpu_delta = data['cpu_stats']['system_cpu_usage'] - data['precpu_stats']['cpu_usage']['total_usage']
    number_cpus = data['cpu_stats']['online_cpus']
    json['cpu_usage_percentage'] = (cpu_delta / system_cpu_delta) * number_cpus * 100.0
    json['number_cpus'] = number_cpus
    json['total_usage'] = data['cpu_stats']['cpu_usage']['total_usage']
    json['usage_in_kernelmode'] = data['cpu_stats']['cpu_usage']['usage_in_kernelmode']
    json['usage_in_usermode'] = data['cpu_stats']['cpu_usage']['usage_in_usermode']
    
    # cache 값은 있을 수도 있고 없을 수도 있기에 파이썬에서 삼항연산자를 활용해서 나타냄.
    # 캐시되어 있다면 그 값을 사용하고 없다면 0을 사용함.
    temp_dict = data['memory_stats']['stats']
    cache_value = data['memory_stats']['stats']['cache'] if (temp_dict.get('cache') != None) else 0

    available_memory = data['memory_stats']['limit'] - cache_value
    used_memory = data['memory_stats']['usage'] - 0
    json['memory_usage'] = (used_memory/available_memory) * 100.0
    json['used_memory'] = used_memory
    json['available_memory'] = available_memory

    json['tx_bytes'] = data['networks']['eth0']['tx_bytes']
    json['tx_packets'] = data['networks']['eth0']['tx_packets']
    json['rx_bytes'] = data['networks']['eth0']['rx_bytes']
    json['rx_packets'] = data['networks']['eth0']['rx_packets']

    return json

# cpu 사용량이 20% 이하인 컨테이너 찾기
def find_free(list):
    for i in range(len(list)):
        print()
        print(str(i)+"th container Info")
        for data in list[i].stats(decode=True):
            stats_info = docker_stats_convertor(data)

            container_name = stats_info['name']
            cpu_usage = stats_info['cpu_usage_percentage']

            if(cpu_usage <= 20) :
                return container_name

            break
    return 0

# 각각 CPU 스케줄링 시 차지 비율 조정 (2배 하향, 2배 상향)
def s_cpu(up_c, down_c):
    down_msg = "docker update --cpu-shares 512 "+down_c 
    up_msg = "docker update --cpu-shares 2048 "+up_c
    os.system(down_msg)
    os.system(up_msg)
    return 



while(True):
    # 도커 클라이언트를 불러옴
    client = docker.from_env()
    # 도커 클라이언트를 통해 컨테이너 리스트를 불러옴
    list = client.containers.list()

    # 1. 수행중인 컨테이너 수, 호스트 메모리 크기 총량(MB), 호스트 CPU 정보 출력
    # 리스트의 길이를 컨테이너의 개수로 사용
    number_of_containers = len(list)
    # psutil 라이브러리를 활용해서 호스트의 전체 메모리를 계산함
    memory_limit = str(round(psutil.virtual_memory().total / (1024.0 **3) * 1024))+"MB"
    # os 라이브러리를 이용해서 cpu의 개수를 가져옴
    cpu_count = os.cpu_count()
    os_name = platform.system()
    processor_name = platform.processor()
    os_version = platform.version()
    # cpu 개수와 100을 곱해서 전체 사용량을 주어진 공식대로 표현함
    cpu_usage_limit = str(100*cpu_count) + str("%")


    # 위의 정보를 이용하여 f-string 포맷팅을 이용하여 출력
    print()
    print("=================================================================")
    print("<<HOST Info>>")
    print(f'OS : {os_name} {os_version}')
    print(f'Number of CPU : {cpu_count}')
    print(f'Limit usage of CPU : {cpu_usage_limit}')
    print(f'Memory : {memory_limit}')
    print("=================================================================")


    print()
    print("=================================================================")
    print("<<Containers Info>>")
    # 2. 각 컨테이너에 대한 동적 자원 정보 및 정적 자원 정보
    # stats 정보를 이용해서 f-string 포맷팅을 이용하여 출력
    for i in range(len(list)):
        up_c = ""
        print()
        for data in list[i].stats(decode=True):
            stats_info = docker_stats_convertor(data)
            IP = stats_info['IP']
            PID = stats_info['PID']
            container_name = stats_info['name'][1:]
            allocated_cpu = stats_info['allocated_cpu_number']
            allocated_memory = stats_info['allocated_memory_size']
            cpu_usage = stats_info['cpu_usage_percentage']
            occupied_memory = stats_info['available_memory']
            used_memory = stats_info['used_memory']
            tx_bytes = stats_info['tx_bytes']
            tx_packets = stats_info['tx_packets']
            rx_bytes = stats_info['rx_bytes']
            rx_packets = stats_info['rx_packets']
            
            print(f'<{container_name} INFO>')
            print(f'PID : {PID}')
            print(f'Allocated CPU : {allocated_cpu}')
            print(f'Allocated Memory : {allocated_memory}')
            print()
            print('> Dynamic Occupy Resource Info')
            print(f'CPU Usage : {cpu_usage}')
            print(f'Occupied Memory : {occupied_memory}')
            print(f'Used Memory : {used_memory}')
            print(f'tx(bytes) : {tx_bytes}')
            print(f'tx(packets) : {tx_packets}')
            print(f'rx(bytes) : {rx_bytes}')
            print(f'rx(packets) : {rx_packets}')
            print()
            print("-----------------------------------------------")


            if(cpu_usage >= 80):
                up_c = container_name
                down_c = find_free(list)
                if down_c != 0:
                    s_cpu(up_c, down_c)
                    print()
                    print("=================================================================")
                    print(f"<container: {up_c}> get additional cpu resource by <container: {down_c}>")
                    print("=================================================================")

            break

    # 3. 호스트 유휴 자원 정보
    print()
    print("=================================================================")
    print("<<Host Free Resource Info>>")
    # psutil 라이브러리를 활용하여 현재 사용 중인 cpu 사용률에서 전체 cpu 사용 가능율을 빼준다.
    cpu_idle = cpu_count*(100 - psutil.cpu_percent())
    print("CPU IDLE : "+str(cpu_idle))
    # pstuil 라이브러리를 활용하여 전체 메모리에서 사용 중인 메모리를 빼준다.
    free_memory = str(round((psutil.virtual_memory().total - psutil.virtual_memory().used) / (1024.0 **3) * 1024))+"MB"
    print("MEMORY FREE : "+free_memory)
    print("=================================================================")

    time.sleep(2)