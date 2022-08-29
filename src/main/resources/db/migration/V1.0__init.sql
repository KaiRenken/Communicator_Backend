create table chat
(
    id               uuid PRIMARY KEY,
    name             varchar(1024)                       not null
);

create table message
(
    id               uuid PRIMARY KEY,
    chat_ref_id      uuid                                not null,
    content          varchar(1024)                       not null
);