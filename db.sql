create table rentalpoint
(
    id      serial            not null
        constraint rentalpoint_pkey
            primary key,
    name    varchar(255)      not null,
    address varchar(255)      not null,
    version integer default 1 not null
);

alter table rentalpoint
    owner to postgres;

create unique index rentalpoint_name_address_unique
    on rentalpoint (name, address);

INSERT INTO public.rentalpoint (id, name, address, version) VALUES (1, 'Прокат №1', 'г. Пермь, ул. Ленина 1', 1);
INSERT INTO public.rentalpoint (id, name, address, version) VALUES (2, 'Прокат №2', 'г. Пермь, ул. Попова 1', 1);
INSERT INTO public.rentalpoint (id, name, address, version) VALUES (3, 'Прокат №3', 'г. Пермь, ул. Куйбышева 1', 1);

create table carbrand
(
    id      serial            not null
        constraint carbrand_pkey
            primary key,
    name    varchar(255)      not null
        constraint carbrand_name_key
            unique,
    version integer default 1 not null
);

alter table carbrand
    owner to postgres;

INSERT INTO public.carbrand (id, name, version) VALUES (1, 'KIA', 1);
INSERT INTO public.carbrand (id, name, version) VALUES (3, 'Renault', 1);
INSERT INTO public.carbrand (id, name, version) VALUES (4, 'Ford', 1);

create table carmodel
(
    id         serial            not null
        constraint carmodel_pkey
            primary key,
    name       varchar(255)      not null,
    carbrandid integer           not null
        constraint fk_carmodel__carbrandid
            references carbrand,
    version    integer default 1 not null
);

alter table carmodel
    owner to postgres;

create index carmodel_carbrandid_idx
    on carmodel (carbrandid);

INSERT INTO public.carmodel (id, name, carbrandid, version) VALUES (1, 'Rio', 1, 1);
INSERT INTO public.carmodel (id, name, carbrandid, version) VALUES (2, 'Logan', 3, 1);
INSERT INTO public.carmodel (id, name, carbrandid, version) VALUES (3, 'Optima', 1, 1);
INSERT INTO public.carmodel (id, name, carbrandid, version) VALUES (4, 'Sandero', 3, 1);
INSERT INTO public.carmodel (id, name, carbrandid, version) VALUES (5, 'Focus', 4, 1);
INSERT INTO public.carmodel (id, name, carbrandid, version) VALUES (6, 'Mondeo', 4, 1);

create table car
(
    id            serial            not null
        constraint car_pkey
            primary key,
    carmodelid    integer           not null
        constraint fk_car__carmodelid
            references carmodel,
    platenumber   varchar(10)       not null
        constraint car_platenumber_key
            unique,
    rentalpointid integer
        constraint fk_car__rentalpointid
            references rentalpoint,
    version       integer default 1 not null
);

alter table car
    owner to postgres;

create index car_carmodelid_idx
    on car (carmodelid);

create index car_rentalpointid_idx
    on car (rentalpointid);

create index car_platenumber_idx
    on car (platenumber);

INSERT INTO public.car (id, carmodelid, platenumber, rentalpointid, version) VALUES (4, 3, 'A103AA159', 2, 1);
INSERT INTO public.car (id, carmodelid, platenumber, rentalpointid, version) VALUES (5, 4, 'A104AA159', 2, 1);
INSERT INTO public.car (id, carmodelid, platenumber, rentalpointid, version) VALUES (6, 5, 'A105AA159', 3, 1);
INSERT INTO public.car (id, carmodelid, platenumber, rentalpointid, version) VALUES (7, 6, 'A106AA159', 3, 1);
INSERT INTO public.car (id, carmodelid, platenumber, rentalpointid, version) VALUES (3, 2, 'A102AA159', 1, 2);
INSERT INTO public.car (id, carmodelid, platenumber, rentalpointid, version) VALUES (2, 1, 'A101AA159', null, 4);

create table client
(
    id           serial                 not null
        constraint client_pkey
            primary key,
    name         varchar(100)           not null,
    surname      varchar(100)           not null,
    patronymic   varchar(100) default ''::character varying,
    drivernumber varchar(15)            not null
        constraint client_drivernumber_key
            unique,
    version      integer      default 1 not null
);

alter table client
    owner to postgres;

create index client_drivernumber_idx
    on client (drivernumber);

INSERT INTO public.client (id, name, surname, patronymic, drivernumber, version) VALUES (3, 'Алексей', 'Сергеев', 'Александрович', '112200003', 1);
INSERT INTO public.client (id, name, surname, patronymic, drivernumber, version) VALUES (5, 'aaa2', 'ыыы', 'ввв', '123123123', 2);
INSERT INTO public.client (id, name, surname, patronymic, drivernumber, version) VALUES (6, 'ййй2', 'ццц', 'ууу', '456456456', 1);
INSERT INTO public.client (id, name, surname, patronymic, drivernumber, version) VALUES (2, 'Петр', 'Сидоров', '', '112200002', 1);
INSERT INTO public.client (id, name, surname, patronymic, drivernumber, version) VALUES (1, 'Иван', 'Иванов', 'Иванович', '112200001', 3);

create table proguser
(
    id         serial                 not null
        constraint user_pkey
            primary key,
    name       varchar(100)           not null,
    surname    varchar(100)           not null,
    patronymic varchar(100) default ''::character varying,
    username   varchar(30)            not null
        constraint user_username_key
            unique,
    password   varchar(100)           not null,
    version    integer      default 1 not null
);

alter table proguser
    owner to postgres;

create index user_username_idx
    on proguser (username);

INSERT INTO public.proguser (id, name, surname, patronymic, username, password, version) VALUES (1, 'Сергей', 'Сергеев', 'Сергеевич', 'user1', '$2a$10$1Ot1IV3zMXSC1jK245EtTemR69ltqUK.I105YjXbTMF6/o/XMyjtq', 1);

create table rentalhistory
(
    id               serial            not null
        constraint rentalhistory_pkey
            primary key,
    datebeg          timestamp         not null,
    dateend          timestamp,
    proguseridbeg    integer           not null
        constraint fk_rentalhistory__proguseridbeg
            references proguser,
    clientid         integer           not null
        constraint fk_rentalhistory__clientid
            references client,
    carid            integer           not null
        constraint fk_rentalhistory__carid
            references car,
    rentalpointidbeg integer           not null
        constraint fk_rentalhistory__rentalpointidbeg
            references rentalpoint,
    rentalpointidend integer
        constraint fk_rentalhistory__rentalpointidend
            references rentalpoint,
    version          integer default 1 not null,
    proguseridend    integer
        constraint fk_rentalhistory__proguseridend
            references proguser
);

alter table rentalhistory
    owner to postgres;

create index rentalhistory_proguseridbeg_idx
    on rentalhistory (proguseridbeg);

create index rentalhistory_clientid_idx
    on rentalhistory (clientid);

create index rentalhistory_carid_idx
    on rentalhistory (carid);

create index rentalhistory_rentalpointidbeg_idx
    on rentalhistory (rentalpointidbeg);

create index rentalhistory_rentalpointidend_idx
    on rentalhistory (rentalpointidend);

create index rentalhistory_proguseridend_idx
    on rentalhistory (proguseridend);

INSERT INTO public.rentalhistory (id, datebeg, dateend, proguseridbeg, clientid, carid, rentalpointidbeg, rentalpointidend, version, proguseridend) VALUES (1, '2021-02-01 08:00:00.000000', '2021-02-02 21:00:00.000000', 1, 1, 2, 1, 2, 1, 1);
INSERT INTO public.rentalhistory (id, datebeg, dateend, proguseridbeg, clientid, carid, rentalpointidbeg, rentalpointidend, version, proguseridend) VALUES (2, '2021-02-03 12:00:00.000000', '2021-02-04 12:00:00.000000', 1, 2, 3, 1, 1, 1, 1);
INSERT INTO public.rentalhistory (id, datebeg, dateend, proguseridbeg, clientid, carid, rentalpointidbeg, rentalpointidend, version, proguseridend) VALUES (3, '2021-02-09 16:00:00.000000', '2021-02-10 21:00:00.000000', 1, 3, 4, 1, 2, 1, 1);
INSERT INTO public.rentalhistory (id, datebeg, dateend, proguseridbeg, clientid, carid, rentalpointidbeg, rentalpointidend, version, proguseridend) VALUES (4, '2021-02-05 21:00:00.000000', '2021-02-06 20:00:00.000000', 1, 2, 2, 2, 1, 1, 1);
INSERT INTO public.rentalhistory (id, datebeg, dateend, proguseridbeg, clientid, carid, rentalpointidbeg, rentalpointidend, version, proguseridend) VALUES (5, '2021-02-07 22:00:00.000000', '2021-02-08 20:00:00.000000', 1, 3, 3, 2, 3, 1, 1);
INSERT INTO public.rentalhistory (id, datebeg, dateend, proguseridbeg, clientid, carid, rentalpointidbeg, rentalpointidend, version, proguseridend) VALUES (6, '2021-02-01 22:00:00.000000', '2021-02-02 22:00:00.000000', 1, 3, 4, 2, 1, 1, 1);
INSERT INTO public.rentalhistory (id, datebeg, dateend, proguseridbeg, clientid, carid, rentalpointidbeg, rentalpointidend, version, proguseridend) VALUES (7, '2021-01-06 22:00:00.000000', '2021-01-07 08:00:00.000000', 1, 1, 2, 3, 2, 1, 1);
INSERT INTO public.rentalhistory (id, datebeg, dateend, proguseridbeg, clientid, carid, rentalpointidbeg, rentalpointidend, version, proguseridend) VALUES (8, '2021-01-08 21:00:00.000000', '2021-01-08 23:00:00.000000', 1, 1, 3, 3, 2, 1, 1);
INSERT INTO public.rentalhistory (id, datebeg, dateend, proguseridbeg, clientid, carid, rentalpointidbeg, rentalpointidend, version, proguseridend) VALUES (9, '2021-01-11 19:00:00.000000', '2021-01-11 22:00:00.000000', 1, 3, 4, 3, 3, 1, 1);
INSERT INTO public.rentalhistory (id, datebeg, dateend, proguseridbeg, clientid, carid, rentalpointidbeg, rentalpointidend, version, proguseridend) VALUES (10, '2021-01-08 12:00:00.000000', '2021-01-08 15:00:00.000000', 1, 2, 3, 3, 1, 1, 1);
INSERT INTO public.rentalhistory (id, datebeg, dateend, proguseridbeg, clientid, carid, rentalpointidbeg, rentalpointidend, version, proguseridend) VALUES (11, '2021-02-07 11:00:00.000000', '2021-02-07 14:00:00.000000', 1, 1, 3, 2, 1, 1, 1);
INSERT INTO public.rentalhistory (id, datebeg, dateend, proguseridbeg, clientid, carid, rentalpointidbeg, rentalpointidend, version, proguseridend) VALUES (12, '2021-02-03 02:00:00.000000', '2021-02-03 10:00:00.000000', 1, 3, 3, 1, 3, 1, 1);
INSERT INTO public.rentalhistory (id, datebeg, dateend, proguseridbeg, clientid, carid, rentalpointidbeg, rentalpointidend, version, proguseridend) VALUES (17, '2021-02-11 21:38:37.263000', '2021-02-12 19:38:37.263000', 1, 2, 3, 1, 1, 1, 1);
INSERT INTO public.rentalhistory (id, datebeg, dateend, proguseridbeg, clientid, carid, rentalpointidbeg, rentalpointidend, version, proguseridend) VALUES (18, '2021-02-12 11:28:35.361000', null, 1, 2, 2, 1, null, 0, null);