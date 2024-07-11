--liquibase formatted sql

--changeset venikovdi:05-vehicle_number-column-change

alter table carpark.tbl_vehicle
    modify column number varchar(9) not null;