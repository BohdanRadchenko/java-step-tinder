create table if not exists chat_id
(
    id serial
        constraint chat_id_pk
            primary key,
    user_id int
        constraint chat_id_users_id_fk
            references users (id),
    name varchar(255)
);

create table if not exists chat_user
(
    chat_id  int not null
        constraint chat_user_chat_id_id_fk
            references chat_id
            on update cascade on delete cascade,
    user_id int not null
        constraint chat_user_users_id_fk
            references users (id)
            on update cascade,
    constraint chat_user_pk
        primary key (chat_id, user_id)
);

create table if not exists messages
(
    message_id serial
        constraint messages_pk
            primary key,
    chat_id int
        constraint messages_chat_id_id_fk
            references chat_id
            on update cascade on delete cascade,
    user_id int
        constraint messages_users_id_fk
            references users (id)
            on update cascade,
    content text,
    created_at timestamp with time zone default now() ,
    updated_at timestamp with time zone default now()
);

create index messages_chat_id_index
    on messages (chat_id);

CREATE TRIGGER update_messages_updated_at
    BEFORE UPDATE
    ON public.messages
    FOR EACH ROW
    EXECUTE PROCEDURE update_updated_at();