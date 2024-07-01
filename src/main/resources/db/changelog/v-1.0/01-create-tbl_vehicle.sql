--liquibase formatted sql

--changeset venikovdi:01-create-tbl_vehicle

create table carpark.tbl_vehicle (
    vehicle_id int primary key auto_increment,
    price int not null,
    mileage int not null,
    release_year int not null,
    color varchar(100)
);