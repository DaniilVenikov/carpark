package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.data.dto.VehicleDto;
import com.venikovdi.carpark.entity.Brand;
import com.venikovdi.carpark.entity.Driver;
import com.venikovdi.carpark.entity.Enterprise;
import com.venikovdi.carpark.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface VehicleToVehicleDtoMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "mileage", source = "mileage")
    @Mapping(target = "releaseYear", source = "releaseYear")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "brandId", source = "brand")
    @Mapping(target = "enterpriseId", source = "enterprise")
    @Mapping(target = "drivers", source = "drivers")
    @Mapping(target = "purchaseDatetime", source = "vehicle", qualifiedByName = "purchaseDatetime")
    VehicleDto map(Vehicle vehicle);

    default Integer mapBrand(Brand brand) {
        return brand == null ? null : brand.getId();
    }
    default Integer mapEnterprise(Enterprise enterprise) {
        return enterprise == null ? null : enterprise.getId();
    }
    default List<Integer> mapDrivers(Set<Driver> drivers) {
        if(drivers == null) {
            return List.of();
        }
        return drivers
                .stream()
                .map(Driver::getId)
                .toList();
    }
    @Named("purchaseDatetime")
    default ZonedDateTime purchaseDatetime(Vehicle vehicle) {
        return vehicle.getPurchaseDatetime().withZoneSameInstant(vehicle.getEnterprise().getTimezone());
    }
}
