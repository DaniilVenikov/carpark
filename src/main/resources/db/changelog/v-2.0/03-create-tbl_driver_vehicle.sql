--liquibase formatted sql

--changeset venikovdi:03-create-tbl_driver_vehicle


create table carpark.tbl_driver_vehicle(
  driver_vehicle_id int primary key auto_increment,
  driver_id int not null,
  vehicle_id int not null,
  constraint fk_driver_vehicle foreign key (driver_id) references carpark.tbl_driver(driver_id),
  constraint fk_vehicle_driver foreign key (vehicle_id) references carpark.tbl_vehicle(vehicle_id),
  constraint uq_driver_vehicle unique (driver_id, vehicle_id)
);

insert into carpark.tbl_driver_vehicle(driver_id, vehicle_id)
values (1, 1),
       (1, 5),
       (2, 3),
       (3, 2),
       (3, 5);