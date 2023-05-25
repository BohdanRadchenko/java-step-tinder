create table if not exists likes
(
    like_id   serial
        primary key
        unique,
    user_from integer not null,
    user_to   integer not null,
    is_liked  boolean not null,
    is_match  boolean not null
);
