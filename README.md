# Computer-Application-Design
2022-2 CAD TUK Project

### 빌드 방법
cd AP_{Service}
./gradlew build

### 실행 방법

*  실행  
docker-compose up -d --build  
  
* 필요한 모듈 다운로드  
pip docker-compose==1.29.2  
pip dockerpty==0.4.1  
pip psutil==5.9.4  
pip platformdirs==2.5.1  
  
* 모니터링을 위한 모듈 다운로드  
pip install -U pip==20.3  
pip install --use-deprecated=legacy-resolver -r docker==4.4.1  
  
* 모니터링&자원할당 모듈 실행  
python3 monitoring/monitoring.py  
  
* 헬스체크 모듈을 위한 docker 최신 버전 다운로드  
pip install docker  
  
* 헬스체크 모듈 실행  
python healthcheck/health_check.py  

* 블루 그린 배포 모듀 실행
python blue_green_deployment_module.py
