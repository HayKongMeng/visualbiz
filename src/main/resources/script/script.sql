CREATE DATABASE virtualbiz_db;
create table user_tb
(
    user_id        serial
        primary key,
    username       varchar(255) not null,
    gender         varchar(50)  not null,
    password       varchar(255) not null,
    address        varchar(255) not null,
    profile_img    varchar(255) not null,
    "current_role" varchar(100) not null,
    email          varchar(100)
);


create table otps_tb
(
    otp_id   serial   not null
        constraint opts_tb_pkey
            primary key,
    otp_code   varchar(6),
    issued_at  timestamp default now(),
    verify     boolean   default false,
    user_id    integer
        constraint opts_user_fk
            references user_tb
            on update cascade on delete cascade,
    expiration timestamp not null
);



create table role_tb
(
    role_id   serial
        primary key,
    role_name varchar(255)
);



create table user_role_tb
(
    user_role_id serial
        primary key,
    role_id      integer
        constraint role_user_fk
            references role_tb
            on update cascade on delete cascade,
    user_id      integer
        constraint user_fk
            references user_tb
            on update cascade on delete cascade
);



create table shop_type_tb
(
    shop_type_id serial
        primary key,
    shop_type    varchar(50)
);



create table shop_tb
(
    shop_id          serial
        primary key,
    shop_name        varchar(255) not null,
    start_date       timestamp,
    shop_profile_img text,
    shop_cover_img   text,
    location         varchar(255),
    working_hour     integer,
    phone_number     varchar(100),
    description      varchar(255),
    user_id          integer
        constraint shop_user_fk
            references user_tb
            on update cascade on delete cascade,
    shop_type_id     integer
        constraint shop_type_fk
            references shop_type_tb,
    email            varchar(255),
    is_active        boolean default true,
    end_date         timestamp,
    is_available     boolean default true
);



create table category_tb
(
    category_id   serial
        primary key,
    category_name varchar(255)
);

create table service_tb
(
    service_id          serial
        primary key,
    service_name        varchar(255),
    service_price       double precision,
    service_description varchar(255),
    service_image       varchar(255),
    is_active           boolean default true,
    category_id         integer
        constraint service_user_fk
            references category_tb
            on update cascade on delete cascade
);


create table service_shop_tb
(
    service_shop_id serial
        primary key,
    shop_id         integer
        constraint shop_user_fk
            references shop_tb
            on update cascade on delete cascade,
    service_id      integer
        constraint service_user_fk
            references service_tb
            on update cascade on delete cascade
);

create table product_tb
(
    product_id          serial
        primary key,
    product_name        varchar(255),
    unit_price          double precision,
    product_description varchar(255),
    product_qty         bigint,
    product_img         varchar(255),
    expired_date        timestamp,
    category_id         integer
        constraint product_category_fk
            references category_tb
            on update cascade on delete cascade,
    barcode             varchar(100),
    is_active           boolean default true
);



create table product_shop_tb
(
    product_shop_id serial
        primary key,
    shop_id         integer
        constraint shop_product_fk
            references shop_tb
            on update cascade on delete cascade,
    product_id      integer
        constraint product_shop_fk
            references product_tb
            on update cascade on delete cascade
);



create table import_product_tb
(
    import_id   serial
        primary key,
    quantity    bigint,
    import_date timestamp
);



create table import_product_detail_tb
(
    import_detail_id serial
        primary key,
    import_id        integer
        constraint import_detail_fk
            references import_product_tb
            on update cascade on delete cascade,
    product_id       integer
        constraint product_detail_fk
            references product_tb
            on update cascade on delete cascade
);


create table bookmark_tb
(
    bookmark_id serial
        primary key,
    user_id     integer
        constraint user_bookmark_fk
            references user_tb
            on update cascade on delete cascade,
    product_id  integer
        constraint product_bookmark_fk
            references product_tb
            on update cascade on delete cascade
);



create table product_bookmark_tb
(
    pro_bookmark_id serial
        primary key,
    product_id      integer
        constraint product_bookmark_fk
            references product_tb
            on update cascade on delete cascade,
    bookmark_id     integer
        constraint bookmark_product_fk
            references bookmark_tb
            on update cascade on delete cascade
);



create table serv_bookmark_tb
(
    serv_bookmark_id serial
        primary key,
    service_id       integer
        constraint service_bookmark_fk
            references service_tb
            on update cascade on delete cascade,
    bookmark_id      integer
        constraint bookmark_service_fk
            references bookmark_tb
            on update cascade on delete cascade
);



create table promotion_tb
(
    promotion_id          serial
        primary key,
    percentage            double precision,
    start_date            timestamp,
    end_date              timestamp,
    shop_id               integer
        constraint shop_promotion_fk
            references shop_tb
            on update cascade on delete cascade,
    promotion_title       varchar(255),
    promotion_description varchar(255),
    promotion_img         varchar(255),
    is_expired            boolean default true
);



create table product_promotion_tb
(
    pro_promotion_id serial
        primary key,
    product_id       integer
        constraint product_promotion_fk
            references product_tb
            on update cascade on delete cascade,
    promotion_id     integer
        constraint promotion_product_fk
            references promotion_tb
            on update cascade on delete cascade
);



create table service_promotion_tb
(
    serv_promotion_id serial
        primary key,
    service_id        integer
        constraint service_promotion_fk
            references service_tb
            on update cascade on delete cascade,
    promotion_id      integer
        constraint promotion_service_fk
            references promotion_tb
            on update cascade on delete cascade
);

create table event_tb
(
    event_id          serial
        primary key,
    event_image       varchar(255),
    event_description varchar(255),
    event_address     varchar(255),
    start_date        timestamp,
    end_date          timestamp,
    shop_id           integer
        constraint shop_event_fk
            references shop_tb
            on update cascade on delete cascade,
    event_title       varchar(255),
    is_active         boolean default true
);


create table event_bookmark_tb
(
    event_bookmark_id serial
        primary key,
    event_id          integer
        constraint event_bookmark_fk
            references event_tb
            on update cascade on delete cascade,
    bookmark_id       integer
        constraint bookmark_event_fk
            references bookmark_tb
            on update cascade on delete cascade
);



create table comment_tb
(
    comment_id          serial
        primary key,
    comment_description varchar(255),
    comment_date        date,
    user_id             integer
        constraint user_comment_fk
            references user_tb
            on update cascade on delete cascade,
    event_id            integer
        constraint event_comment_fk
            references event_tb
            on update cascade on delete cascade
);



create table status_tb
(
    status_id   serial
        primary key,
    status_type varchar(100)
);



create table book_tb
(
    book_id   serial
        primary key,
    status_id integer
        constraint status_book_fk
            references status_tb
            on update cascade on delete cascade,
    user_id   integer
        constraint user_book_fk
            references user_tb
            on update cascade on delete cascade,
    shop_id   integer
        constraint shop_book_fk
            references shop_tb
);



create table order_tb
(
    order_id      serial
        primary key,
    order_address varchar(255),
    order_date    timestamp,
    user_id       integer
        constraint user_order_fk
            references user_tb
            on update cascade on delete cascade,
    status_id     integer
        constraint status_order_fk
            references status_tb
            on update cascade on delete cascade,
    shop_id       integer
        constraint shop_order_fk
            references shop_tb
);



create table order_product_tb
(
    order_product_id serial
        primary key,
    order_id         integer
        constraint order_user_fk
            references order_tb
            on update cascade on delete cascade,
    product_id       integer
        constraint order_product_tb_product_tb_product_id_fk
            references product_tb,
    qty              integer
);



create table book_service_tb
(
    book_service_id serial
        primary key,
    start_date      timestamp,
    book_id         integer
        constraint book_service_fk
            references book_tb
            on update cascade on delete cascade,
    service_id      integer
        constraint service_book_fk
            references service_tb
            on update cascade on delete cascade,
    end_date        timestamp
);



create table notification_tb
(
    notification_id      serial
        primary key,
    notification_type    varchar(100),
    notification_message varchar(255),
    notification_date    timestamp,
    isread               boolean default false,
    reciever_id          integer,
    sender_id            integer
        constraint notification_tb_user_tb__user_id_fk
            references user_tb
);


create table book_notification_tb
(
    book_notification_tb serial
        primary key,
    notification_id      integer
        constraint notification_book_fk
            references notification_tb
            on update cascade on delete cascade,
    book_id              integer
        constraint book_notification_fk
            references book_tb
            on update cascade on delete cascade
);


create table order_notification_tb
(
    order_notification_id serial
        primary key,
    notification_id       integer
        constraint notification_order_fk
            references notification_tb
            on update cascade on delete cascade,
    order_id              integer
        constraint order_notification_fk
            references order_tb
            on update cascade on delete cascade
);



create table rate_feedback_tb
(
    feedback_id serial
        primary key,
    feedback    varchar(255),
    rate        integer,
    user_id     integer
        constraint user_rate_fk
            references user_tb
            on update cascade on delete cascade
);



create table order_feedback_tb
(
    order_feedback_id serial
        primary key,
    feedback_id       integer
        constraint feedback_order_fk
            references rate_feedback_tb
            on update cascade on delete cascade,
    order_id          integer
        constraint order_feedback_fk
            references order_tb
            on update cascade on delete cascade
);


create table book_feedback_tb
(
    book_feedback_id serial
        primary key,
    feedback_id      integer
        constraint feedback_order_fk
            references rate_feedback_tb
            on update cascade on delete cascade,
    order_id         integer
        constraint order_feedback_fk
            references order_tb
            on update cascade on delete cascade
);


create table service_schedule_tb
(
    schedule_id serial
        primary key,
    block_date  timestamp,
    service_id  integer
        constraint service_schedule_fk
            references service_tb
);
