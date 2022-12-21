import os

blue_container_name = input("blue_container_name : ")

green_container_image_name = input("green_container_image_name : ")
green_container_dockerfile_location = input("green_container_dockerfile_location : ")
green_container_port = input("green_container_port : ")
green_container_name = input("green_container_name : ")

os.system(f"docker build -t {green_container_image_name} {green_container_dockerfile_location}")
os.system(f"docker run -p {green_container_port}:{green_container_port} --name {green_container_name} -e SPRING_DATASOURCE_URL=\"jdbc:mysql://mysql:3306/ce?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&serverTimezone=Asia/Seoul\" -e SPRING_DATASOURCE_USERNAME=root -e SPRING_DATASOURCE_PASSWORD=1234 --network computer-application-design_msa {green_container_image_name}")
os.system(f"docker stop {blue_container_name}")