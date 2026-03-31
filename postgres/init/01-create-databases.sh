#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE lifesim_auth;
    GRANT ALL PRIVILEGES ON DATABASE lifesim_auth TO $POSTGRES_USER;

    CREATE DATABASE lifesim_need;
    GRANT ALL PRIVILEGES ON DATABASE lifesim_need TO $POSTGRES_USER;

    CREATE DATABASE lifesim_character;
    GRANT ALL PRIVILEGES ON DATABASE lifesim_character TO $POSTGRES_USER;

    CREATE DATABASE lifesim_engine;
    GRANT ALL PRIVILEGES ON DATABASE lifesim_engine TO $POSTGRES_USER;

    CREATE DATABASE lifesim_world;
    GRANT ALL PRIVILEGES ON DATABASE lifesim_world TO $POSTGRES_USER;
EOSQL

echo "Databases created successfully"