DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS form;
DROP TABLE IF EXISTS "likes";
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS guests;
DROP TABLE IF EXISTS images;

create table form
(
    id         bigserial primary key not null,
    man        boolean default false not null,
    woman      boolean default false not null,
    friendship boolean default false not null,
    love       boolean default false not null,
    sex        boolean default false not null,
    flirt      boolean default false not null
);

create table "user"
(
    id          bigserial primary key                      not null,
    username    varchar(255)                               not null unique,
    first_name  varchar(255)                default '',
    last_name   varchar(255)                default '',
    password    text                        default ''     not null,
    email       varchar(255)                default ''     not null unique,
    gender      varchar(255)                default '',
    birthday    timestamp without time zone,
    description text                        default '',
    is_active   boolean                     default true   not null,
    form_id     bigint unique,
    rate        bigint                      default 0      not null,
    avatar_id   bigint,
    role        varchar(64)                 default 'USER' not null,
    create_ts   timestamp without time zone default now()  not null,
    update_ts   timestamp without time zone,
    delete_ts   timestamp without time zone,
    foreign key (form_id) references form (id)
);

create table likes
(
    id        bigserial primary key                     not null,
    "from"    bigint references "user" (id)             not null,
    "to"      bigint references "user" (id)             not null,
    "like"    boolean                     default true  not null,
    create_ts timestamp without time zone default now() not null,
    unique ("to", "from", "like")
);

create table tags
(
    id        bigserial primary key                     not null,
    tag       varchar(255)                              not null,
    create_ts timestamp without time zone default now() not null,
    user_id   bigint references "user" (id),
    unique (tag, user_id)
);

create table guests
(
    id        bigserial primary key                     not null,
    guest_id  bigint references "user" (id),
    user_id   bigint references "user" (id),
    create_ts timestamp without time zone default now() not null
);

create table images
(
    id          bigserial primary key                     not null,
    name        text                                      not null,
    link        text                                      not null,
    external_id text                                      not null,
    user_id     bigint references "user" (id)             not null,
    deleted     boolean                     default false not null,
    delete_ts   timestamp without time zone,
    create_ts   timestamp without time zone default now() not null
);

INSERT INTO public.form (id,
                         man,
                         woman,
                         friendship,
                         love,
                         sex,
                         flirt)
VALUES (1,
        false,
        false,
        false,
        false,
        false,
        false);

INSERT INTO public."user" (id,
                           username,
                           first_name,
                           last_name,
                           password,
                           email,
                           gender,
                           birthday,
                           description,
                           is_active,
                           form_id,
                           role)
VALUES (1,
        'admin',
        'admin',
        'admin',
        '1000:21442a0a98c8999af3d0d82b741772f7:2a842098fecb2f83a8b37dab1554a4fa4a21bb3df6caee0a54fcf07c2b0010b55e4bec925c0c86eeea6e095e89b47883fee143f50594c1c18dc02170238fe006',
        'matchaschool21@gmail.com',
        'man',
        null,
        'I AM SUPERMAN!',
        true,
        1,
        'ADMIN');