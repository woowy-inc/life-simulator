create table if not exists timezones
(
    timezone_id  varchar     not null primary key,
    abbreviation varchar(20) not null,
    utc_offset   varchar(20) not null,
    msk_offset   varchar(20) not null
);

create table if not exists regions
(
    id              uuid primary key not null,

    okato           varchar(20)      not null,
    oktmo           varchar(20)      not null,
    code            varchar(10)      not null,
    iso_3166_2      varchar(10)      not null,

    label           varchar(255)     not null,
    name            varchar(255)     not null,
    name_en         varchar(255)     not null,
    full_name       varchar(500)     not null,
    unofficial_name varchar(255),

    type            varchar(100)     not null,
    type_short      varchar(20)      not null,
    content_type    varchar(50)      not null,

    population      bigint           not null,
    year_founded    smallint         not null,
    area            integer          not null,
    district        varchar(255)     not null
);

create index if not exists idx_regions_label on regions (label);
create index if not exists idx_regions_code on regions (code);

create table if not exists region_namecases
(
    region_id     uuid         not null references regions (id) on delete cascade,

    nominative    varchar(255) not null,
    genitive      varchar(255) not null,
    dative        varchar(255) not null,
    accusative    varchar(255) not null,
    ablative      varchar(255) not null,
    prepositional varchar(255) not null,
    locative      varchar(255) not null
);

create index if not exists idx_region_namecases_region_id on region_namecases (region_id);

create table if not exists cities
(
    id               uuid primary key not null,
    region_id        uuid             not null references regions (id),
    timezone_id      varchar          not null references timezones (timezone_id),

    okato            varchar(20)      not null,
    oktmo            varchar(20)      not null,

    label            varchar(255)     not null,
    name             varchar(255)     not null,
    name_alt         varchar(255)     not null,
    name_en          varchar(255)     not null,

    type             varchar(100)     not null,
    type_short       varchar(20)      not null,
    content_type     varchar(50)      not null,

    is_dual_name     boolean          not null default false,
    is_capital       boolean          not null default false,

    zip              integer          not null,
    population       bigint           not null,
    year_founded     smallint         not null,
    year_city_status smallint         not null,

    latitude         decimal(10, 7)   not null,
    longitude        decimal(10, 7)   not null
);

create index if not exists idx_cities_region_id on cities (region_id);
create index if not exists idx_cities_timezone_id on cities (timezone_id);
create index if not exists idx_cities_label on cities (label);
create index if not exists idx_cities_name on cities (name);

create table if not exists city_namecases
(
    city_id       uuid         not null references cities (id) on delete cascade,

    nominative    varchar(255) not null,
    genitive      varchar(255) not null,
    dative        varchar(255) not null,
    accusative    varchar(255) not null,
    ablative      varchar(255) not null,
    prepositional varchar(255) not null,
    locative      varchar(255) not null
);

create index if not exists idx_city_namecases_city_id on city_namecases (city_id);