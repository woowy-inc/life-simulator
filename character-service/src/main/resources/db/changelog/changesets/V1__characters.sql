create table characters
(
    id          uuid        not null primary key default gen_random_uuid(),
    user_id     uuid        not null,
    name        varchar(64) not null,
    gender      varchar(16) not null,
    birthday    timestamptz not null,
    location_id uuid        not null,
    world_id    uuid,
    created_at  timestamptz not null
);

create index idx_characters_user_id on characters (user_id);