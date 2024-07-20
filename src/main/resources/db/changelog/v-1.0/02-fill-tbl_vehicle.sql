--liquibase formatted sql

--changeset venikovdi:02-fill-tbl_vehicle

insert into carpark.tbl_vehicle (price, mileage, release_year, color)
values (1500000, 2000, 2024, 'мокрый асфальт'),
       (2000000, 1000000, 2015, 'синий'),
       (700000, 153000, 2010, 'оранжевый'),
       (1250000, 56000, 2021, 'чёрный'),
       (6700000, 10000, 2024, 'белый'),
       (3420000, 83000, 2020, 'голубой'),
       (1640000, 153000, 2018, 'белый')
;