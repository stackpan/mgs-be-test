create table users
(
    id uuid not null primary key,
    username varchar(16) not null unique,
    email varchar(64) not null unique,
    first_name varchar(32) not null,
    last_name varchar(64),
    profile_picture varchar(128),
    password varchar(4096) not null,
    role varchar(50) not null
);
