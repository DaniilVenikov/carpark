package com.venikovdi.carpark.service;

import com.venikovdi.carpark.data.dto.BrandDto;
import com.venikovdi.carpark.mapper.BrandToBrandDtoMapper;
import com.venikovdi.carpark.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandToBrandDtoMapper brandToBrandDtoMapper;


    public List<BrandDto> getAll() {
        return brandRepository.findAll()
                .stream()
                .map(brandToBrandDtoMapper::map)
                .toList();
    }
}
