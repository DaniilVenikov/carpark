package com.venikovdi.carpark.service;

import com.venikovdi.carpark.data.dto.EnterpriseDto;
import com.venikovdi.carpark.mapper.EnterpriseToEnterpriseDtoMapper;
import com.venikovdi.carpark.repository.EnterpriseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final EnterpriseToEnterpriseDtoMapper enterpriseToEnterpriseDtoMapper;

    public Collection<EnterpriseDto> getAllEnterprises() {
        return enterpriseRepository.findAll()
                .stream()
                .map(enterpriseToEnterpriseDtoMapper::map)
                .toList();
    }
}
