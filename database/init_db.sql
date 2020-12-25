DROP TABLE IF EXISTS "likes";
DROP TABLE IF EXISTS guests;
DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS user_ref_tags;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS form;

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
    id              bigserial primary key                      not null,
    username        varchar(255)                               not null unique,
    first_name      varchar(255)                default '',
    last_name       varchar(255)                default '',
    password        text                        default ''     not null,
    email           varchar(255)                default ''     not null unique,
    gender          varchar(255)                default '',
    birthday        timestamp without time zone,
    description     text                        default '',
    is_active       boolean                     default true   not null,
    form_id         bigint unique,
    rate            bigint                      default 0      not null,
    avatar_id       bigint,
    role            varchar(64)                 default 'USER' not null,
    latitude        double precision            default 0,
    longitude       double precision            default 0,
    is_verified     boolean                     default false  not null,
    is_online       boolean                     default false  not null,
    create_ts       timestamp without time zone default now()  not null,
    update_ts       timestamp without time zone,
    delete_ts       timestamp without time zone,
    last_login_date timestamp without time zone,
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
    unique (tag)
);

create table user_ref_tags
(
    tag_id  bigint not null references tags (id),
    user_id bigint not null references "user" (id),
    unique (tag_id, user_id)
);

create table guests
(
    id       bigserial primary key                     not null,
    guest_id bigint references "user" (id),
    user_id  bigint references "user" (id),
    date_ts  timestamp without time zone default now() not null,
    unique (guest_id, user_id)
);

create table images
(
    id          bigserial primary key                     not null,
    name        text                                      not null,
    link        text                                      not null,
    external_id text                                      not null,
    user_id     bigint references "user" (id)             not null,
    create_ts   timestamp without time zone default now() not null
);

create table messages
(
    id        bigserial primary key                     not null,
    "from"    bigint references "user" (id)             not null,
    "to"      bigint references "user" (id)             not null,
    message   text                        default ''    not null,
    create_ts timestamp without time zone default now() not null
);

create table black_list
(
    id        bigserial primary key                     not null,
    "from"    bigint references "user" (id)             not null,
    "to"      bigint references "user" (id)             not null,
    create_ts timestamp without time zone default now() not null,
    unique ("to", "from")
);

create table complaints
(
    id        bigserial primary key                     not null,
    "from"    bigint references "user" (id)             not null,
    "to"      bigint references "user" (id)             not null,
    message   text                        default ''    not null,
    create_ts timestamp without time zone default now() not null,
    unique ("to", "from")
);

INSERT INTO public.form (man,
                         woman,
                         friendship,
                         love,
                         sex,
                         flirt)
VALUES (false,
        false,
        false,
        false,
        false,
        false);

INSERT INTO public."user" (username,
                           first_name,
                           last_name,
                           password,
                           email,
                           gender,
                           birthday,
                           description,
                           is_active,
                           form_id,
                           role,
                           is_verified)
VALUES ('admin',
        'admin',
        'admin',
        '1000:21442a0a98c8999af3d0d82b741772f7:2a842098fecb2f83a8b37dab1554a4fa4a21bb3df6caee0a54fcf07c2b0010b55e4bec925c0c86eeea6e095e89b47883fee143f50594c1c18dc02170238fe006',
        'matchaschool21@gmail.com',
        'man',
        null,
        'I AM SUPERMAN!',
        true,
        (select id from form limit 1),
        'ADMIN',
        true);