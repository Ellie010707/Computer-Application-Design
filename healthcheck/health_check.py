import docker
import time

client = docker.from_env()

while True:
    containers = client.containers.list(all,sparse=True)
    host_info = client.info()
    
    for container in containers:
        container_attrs = container.attrs
        status = container_attrs['Status']
        if "Exited" in status:
            print(container_attrs['Names'][0][1:], "restart")
            container.start()
        elif "unhealthy" in status:
            print(container_attrs['Names'][0][1:], "restart")
            container.restart()
    time.sleep(10)