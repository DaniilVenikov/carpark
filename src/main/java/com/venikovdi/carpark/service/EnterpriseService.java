package com.venikovdi.carpark.service;

import com.venikovdi.carpark.data.dto.EnterpriseDto;
import com.venikovdi.carpark.entity.Manager;
import com.venikovdi.carpark.mapper.EnterpriseToEnterpriseDtoMapper;
import com.venikovdi.carpark.repository.EnterpriseRepository;
import com.venikovdi.carpark.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final ManagerRepository managerRepository;
    private final EnterpriseToEnterpriseDtoMapper enterpriseToEnterpriseDtoMapper;

    public Collection<EnterpriseDto> getAllEnterprises() {
        return enterpriseRepository.findAll()
                .stream()
                .map(enterpriseToEnterpriseDtoMapper::map)
                .toList();
    }

    public Collection<EnterpriseDto> getEnterpriseForManager(String username) {
        Manager manager = managerRepository.findByUsername(username).orElseThrow();

        return manager
                .getEnterprises()
                .stream()
                .map(enterpriseToEnterpriseDtoMapper::map)
                .toList();
    }
}
