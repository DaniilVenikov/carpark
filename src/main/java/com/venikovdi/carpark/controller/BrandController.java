package com.venikovdi.carpark.controller;

import com.venikovdi.carpark.api.BrandApi;
import com.venikovdi.carpark.api.data.BrandResponseData;
import com.venikovdi.carpark.mapper.BrandDtoToBrandResponseDataMapper;
import com.venikovdi.carpark.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class BrandController implements BrandApi {

    private final BrandService brandService;
    private final BrandDtoToBrandResponseDataMapper brandDtoToBrandResponseDataMapper;

    @Override
    public ResponseEntity<Collection<BrandResponseData>> get() {
        return ResponseEntity.ok(
                brandService.getAll()
                .stream()
                .map(brandDtoToBrandResponseDataMapper::map)
                .toList());
    }
}
