#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    -- Auth Service Database
    CREATE DATABASE lifesim_auth;
    GRANT ALL PRIVILEGES ON DATABASE lifesim_auth TO $POSTGRES_USER;
EOSQL

echo "Databases created successfully"