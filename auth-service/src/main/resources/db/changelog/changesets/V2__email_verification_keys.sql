create table if not exists email_verification_keys
(
    key        varchar(100) primary key,
    user_id    uuid        not null references users (id) on delete cascade,
    expires_at timestamptz not null,
    used       boolean     not null default false
);