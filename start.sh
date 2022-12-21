docker-compose up -d --build
python3 monitoring/monitoring.py
python3 healthcheck/healthcheck.py