--liquibase formatted sql

--changeset venikovdi:01-create-tbl_enterprise

create table carpark.tbl_enterprise
(
    enterprise_id int primary key auto_increment,
    title         varchar(150) not null,
    city          varchar(100) not null
);

alter table carpark.tbl_vehicle
    add column enterprise_id int;

alter table carpark.tbl_vehicle
    add constraint fk_enterprise_vehicle foreign key (enterprise_id) references carpark.tbl_enterprise(enterprise_id);


insert into carpark.tbl_enterprise (title, city)
values ('АгроРесурс', 'Белгород'),
       ('Яндекс', 'Москва'),
       ('Ситимобил', 'Воронеж');


update carpark.tbl_vehicle tv
set tv.enterprise_id = 1
where tv.vehicle_id = 1;

update carpark.tbl_vehicle tv
set tv.enterprise_id = 1
where tv.vehicle_id = 5;

update carpark.tbl_vehicle tv
set tv.enterprise_id = 2
where tv.vehicle_id = 3;

update carpark.tbl_vehicle tv
set tv.enterprise_id = 2
where tv.vehicle_id = 4;

update carpark.tbl_vehicle tv
set tv.enterprise_id = 3
where tv.vehicle_id = 2;

update carpark.tbl_vehicle tv
set tv.enterprise_id = 3
where tv.vehicle_id = 6;