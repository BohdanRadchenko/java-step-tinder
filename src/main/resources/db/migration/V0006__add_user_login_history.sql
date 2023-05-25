create table if not exists user_login_history
(
    user_id    integer                  not null
        constraint user_login_history_users_id_fk
            references users
            on update cascade on delete cascade,
    login_time timestamp with time zone not null,
    constraint user_login_history_pk
        primary key (user_id, login_time)
);
