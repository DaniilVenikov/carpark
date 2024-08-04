package com.venikovdi.carpark.service;

import com.venikovdi.carpark.data.dto.EnterpriseDto;
import com.venikovdi.carpark.entity.Enterprise;
import com.venikovdi.carpark.entity.Manager;
import com.venikovdi.carpark.exception.ResourceAlreadyExistsException;
import com.venikovdi.carpark.mapper.EnterpriseDtoToEnterpriseMapper;
import com.venikovdi.carpark.mapper.EnterpriseToEnterpriseDtoMapper;
import com.venikovdi.carpark.repository.EnterpriseRepository;
import com.venikovdi.carpark.repository.ManagerRepository;
import com.venikovdi.carpark.entity.en.ResourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final ManagerRepository managerRepository;
    private final EnterpriseToEnterpriseDtoMapper enterpriseToEnterpriseDtoMapper;
    private final EnterpriseDtoToEnterpriseMapper enterpriseDtoToEnterpriseMapper;

    public Collection<EnterpriseDto> getEnterpriseForManager(String username) {
        Manager manager = managerRepository.findByUsername(username).orElseThrow();

        return manager
                .getEnterprises()
                .stream()
                .map(enterpriseToEnterpriseDtoMapper::map)
                .toList();
    }

    @Transactional
    public EnterpriseDto add(EnterpriseDto enterpriseDto) {
        if(enterpriseRepository.existsByTitle(enterpriseDto.title())) {
            throw new ResourceAlreadyExistsException(ResourceType.ENTERPRISE, enterpriseDto.title());
        }
        var enterpriseEntity = enterpriseDtoToEnterpriseMapper.map(enterpriseDto);
        return enterpriseToEnterpriseDtoMapper.map(enterpriseRepository.save(enterpriseEntity));
    }

    @Transactional
    public Optional<EnterpriseDto> remove(Integer id) {
        var enterprise = enterpriseRepository.findById(id);
        if (enterprise.isEmpty()) {
            return Optional.empty();
        }
        enterpriseRepository.delete(enterprise.get());
        return enterprise.map(enterpriseToEnterpriseDtoMapper::map);
    }

    @Transactional
    public Optional<EnterpriseDto> update(Integer id, EnterpriseDto enterpriseDto) {
        Optional<Enterprise> enterprise = enterpriseRepository.findById(id);
        if (enterprise.isEmpty()){
            return Optional.empty();
        }

        var enterpriseUpdate = enterpriseDtoToEnterpriseMapper.update(enterprise.get(), enterpriseDto);
        return Optional.of(enterpriseToEnterpriseDtoMapper.map(enterpriseRepository.save(enterpriseUpdate)));
    }

    public Collection<EnterpriseDto> getAllEnterprises() {
        return enterpriseRepository.findAll()
                .stream()
                .map(enterpriseToEnterpriseDtoMapper::map)
                .toList();
    }
}
