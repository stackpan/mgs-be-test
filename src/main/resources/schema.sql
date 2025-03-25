create table users
(
    id              uuid          not null primary key,
    username        varchar(50)   not null unique,
    email           varchar(100)  not null unique,
    first_name      varchar(50)   not null,
    last_name       varchar(100),
    profile_picture varchar(200),
    password        varchar(1000) not null,
    role            varchar(20)   not null
);

create table permissions
(
    user_id uuid        not null references users (id),
    name    varchar(50) not null,
    primary key (user_id, name)
);