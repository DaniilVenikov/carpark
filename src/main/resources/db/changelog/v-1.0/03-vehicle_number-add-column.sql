--liquibase formatted sql

--changeset venikovdi:03-vehicle_number-add-column

alter table carpark.tbl_vehicle
add column number varchar(6) not null;

