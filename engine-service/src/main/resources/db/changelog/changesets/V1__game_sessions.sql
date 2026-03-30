create table if not exists game_sessions
(
    character_id uuid        not null primary key,
    status       varchar(20) not null,
    game_time    timestamptz not null,
    started_at   timestamptz not null,
    started_by   uuid        not null,
    tick_number  bigint      not null,
    paused_at    timestamptz
)