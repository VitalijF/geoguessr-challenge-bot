FROM postgres:13.4
RUN apt-get update && apt-get install -y dos2unix
COPY ./create-database.sh /docker-entrypoint-initdb.d/create-database.sh
RUN dos2unix /docker-entrypoint-initdb.d/create-database.sh
