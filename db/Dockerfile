FROM mysql:8.0

ADD ./initdb.d /docker-entrypoint-initdb.d
ADD ./conf.d/my.cnf /etc/mysql/conf.d

EXPOSE 3306

CMD ["mysqld"]