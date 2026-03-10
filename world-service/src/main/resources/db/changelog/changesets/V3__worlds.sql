create table worlds
(
    id           uuid        not null primary key default gen_random_uuid(),
    character_id uuid        not null,
    created_at   timestamptz not null             default now()
)