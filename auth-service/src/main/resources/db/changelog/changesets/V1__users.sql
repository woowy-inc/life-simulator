create table if not exists users
(
    id         uuid primary key default gen_random_uuid(),
    username   varchar(255) not null unique,
    email      varchar(255) not null unique,
    password   varchar(255) not null,
    first_name varchar(255) not null,
    role       varchar(50)  not null
);