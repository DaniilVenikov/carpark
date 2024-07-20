--liquibase formatted sql

--changeset venikovdi:02-create-tbl_driver

create table carpark.tbl_driver
(
    driver_id     int primary key auto_increment,
    full_name     varchar(150) not null,
    salary        int          not null,
    enterprise_id int,
    constraint fk_enterprise_driver foreign key (enterprise_id) references carpark.tbl_enterprise(enterprise_id)
);

insert into carpark.tbl_driver (full_name, salary, enterprise_id)
values ('Лебедев Виктор Максимович', 65700, 1),
       ('Богомолов Алексей Владимирович', 96000, 2),
       ('Гузев Олег Андреевич', 83400, 3);