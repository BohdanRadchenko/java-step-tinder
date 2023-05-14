create table if not exists users
(
    id         serial primary key,
    uuid       varchar(255) unique,
    email      varchar(255)                           not null unique,
    login      varchar(16)                            not null unique,
    password   varchar(255)                           not null,
    created_at timestamp with time zone default now() not null,
    updated_at timestamp with time zone default now() not null
);
