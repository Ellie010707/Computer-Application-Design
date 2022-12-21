docker-compose up -d --build

pip install docker
pip docker-compose==1.29.2
pip dockerpty==0.4.1
pip psutil==5.9.4
pip platformdirs==2.5.1

#execute health check module
python healthcheck/health_check.py


pip install -U pip==20.3
pip install --use-deprecated=legacy-resolver -r docker==4.4.1
#execute monitoring&dynamic allocation module
python3 monitoring/monitoring.py