FROM postgres:latest
ENV POSTGRES_USER="postgres"
ENV POSTGRES_PASSWORD="postgres"
ENV POSTGRES_DB="matcha"
COPY ../backend/src/main/resources/db/sql/init_db.sql /docker-entrypoint-initdb.d/.
ENV TZ="Europe/Moscow"
EXPOSE 5432