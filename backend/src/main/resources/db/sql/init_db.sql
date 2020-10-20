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
    id          bigserial primary key                     not null,
    username    varchar(255)                              not null unique,
    first_name  varchar(255)                default '',
    last_name   varchar(255)                default '',
    password    text                        default ''    not null,
    email       varchar(255)                              not null unique,
    gender      varchar(255),
    birthday    timestamp without time zone,
    description text                        default '',
    is_active   boolean                     default true  not null,
    form_id     bigint unique,
    create_ts   timestamp without time zone default now() not null,
    update_ts   timestamp without time zone,
    delete_ts   timestamp without time zone,
    foreign key (form_id) references form (id)
);

INSERT INTO public."user" (id, username, first_name, last_name, password, email, gender, birthday, description,
                           is_active, form_id, create_ts, update_ts, delete_ts)
VALUES (1, 'admin', '', '',
        '1000:21442a0a98c8999af3d0d82b741772f7:2a842098fecb2f83a8b37dab1554a4fa4a21bb3df6caee0a54fcf07c2b0010b55e4bec925c0c86eeea6e095e89b47883fee143f50594c1c18dc02170238fe006',
        'admin@admin.com', null, null, '', true, null, '2020-10-20 23:33:27.908894', null, null);
