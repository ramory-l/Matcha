FROM postgres:latest
ENV POSTGRES_USER="postgres"
ENV POSTGRES_PASSWORD="postgres"
ENV POSTGRES_DB="matcha"
COPY init_db.sql /docker-entrypoint-initdb.d/.
ENV TZ="Europe/Moscow"
EXPOSE 5432