--liquibase formatted sql

--changeset venikovdi:04-create-tbl_brand


create table carpark.tbl_brand
(
    brand_id         int primary key auto_increment,
    title            varchar(100) not null,
    type             varchar(50)  not null,
    volume_tank      int          not null,
    lifting_capacity int          not null,
    number_seats     int          not null
);

insert into carpark.tbl_brand (title, type, volume_tank, lifting_capacity, number_seats)
values ('Lada', 'CAR', 2, 600, 5),
       ('Chery', 'CAR', 3, 850, 7),
       ('MAZ', 'BUS', 10, 2000, 35),
       ('KIA', 'CAR', 2, 900, 5);

alter table carpark.tbl_vehicle
    add column brand_id int;

alter table carpark.tbl_vehicle
    add constraint fk_brand foreign key (brand_id) references carpark.tbl_brand(brand_id);

update carpark.tbl_vehicle tv
set tv.brand_id = 1
where tv.vehicle_id = 1;

update carpark.tbl_vehicle tv
set tv.brand_id = 2
where tv.vehicle_id = 2;

update carpark.tbl_vehicle tv
set tv.brand_id = 1
where tv.vehicle_id = 3;

update carpark.tbl_vehicle tv
set tv.brand_id = 1
where tv.vehicle_id = 4;

update carpark.tbl_vehicle tv
set tv.brand_id = 3
where tv.vehicle_id = 5;

update carpark.tbl_vehicle tv
set tv.brand_id = 4
where tv.vehicle_id = 6;

update carpark.tbl_vehicle tv
set tv.brand_id = 2
where tv.vehicle_id = 7;