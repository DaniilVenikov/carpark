--liquibase formatted sql

--changeset venikovdi:02-fill-tbl_vehicle

insert into carpark.tbl_vehicle (price, mileage, release_year, color)
values (1500000, 2000, 2024, 'мокрый асфальт'),
       (2000000, 1000000, 2015, 'синий'),
       (700000, 153000, 2010, 'оранжевый');