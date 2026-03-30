create table accounts
(
    id           uuid        not null primary key default gen_random_uuid(),
    character_id uuid        not null references characters (id),
    type         varchar(20) not null,
    currency     varchar(3)  not null,
    status       varchar(20) not null,
    created_at   timestamptz not null
);

create index idx_accounts_character_id on accounts (character_id);

create table account_entries
(
    id          uuid           not null primary key default gen_random_uuid(),
    account_id  uuid           not null references accounts (id),
    amount      numeric(19, 4) not null,
    direction   varchar(15)    not null,
    reason      varchar(35)    not null,
    occurred_at timestamptz    not null
);

create index idx_account_entries_account_id on account_entries (account_id);