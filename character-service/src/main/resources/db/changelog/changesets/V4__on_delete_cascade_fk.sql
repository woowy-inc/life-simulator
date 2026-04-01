alter table accounts
    drop constraint accounts_character_id_fkey,
    add constraint accounts_character_id_fkey
        foreign key (character_id)
            references characters (id)
            on delete cascade;

alter table account_entries
    drop constraint account_entries_account_id_fkey,
    add constraint account_entries_account_id_fkey
        foreign key (account_id)
            references accounts (id)
            on delete cascade;