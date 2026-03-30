create table if not exists needs
(
    id           uuid primary key       default gen_random_uuid(),
    character_id uuid          not null,
    hunger       numeric(5, 2) not null default 100.0,
    sleep        numeric(5, 2) not null default 100.0,
    body         numeric(5, 2) not null default 100.0,
    mental       numeric(5, 2) not null default 100.0,
    social       numeric(5, 2) not null default 100.0,
    health       numeric(5, 2) not null default 100.0,
    happiness    numeric(5, 2) not null default 100.0,
    created_at   timestamptz   not null default now()
);