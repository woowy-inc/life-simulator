FROM postgres:18.1-alpine

COPY init/*.sh /docker-entrypoint-initdb.d/

RUN chmod +x /docker-entrypoint-initdb.d/*.sh

EXPOSE 5432