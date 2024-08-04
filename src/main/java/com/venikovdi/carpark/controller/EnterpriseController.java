package com.venikovdi.carpark.controller;

import com.venikovdi.carpark.api.EnterpriseApi;
import com.venikovdi.carpark.api.data.EnterprisePostRequestData;
import com.venikovdi.carpark.api.data.EnterprisePutRequestData;
import com.venikovdi.carpark.api.data.EnterpriseResponseData;
import com.venikovdi.carpark.data.dto.EnterpriseDto;
import com.venikovdi.carpark.mapper.EnterpriseDtoToEnterpriseResponseDataMapper;
import com.venikovdi.carpark.mapper.EnterprisePostRequestDataToEnterpriseDtoMapper;
import com.venikovdi.carpark.mapper.EnterprisePutRequestDataToEnterpriseDtoMapper;
import com.venikovdi.carpark.service.EnterpriseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EnterpriseController implements EnterpriseApi {
    private final EnterpriseService enterpriseService;
    private final EnterpriseDtoToEnterpriseResponseDataMapper enterpriseDtoToEnterpriseResponseDataMapper;
    private final EnterprisePostRequestDataToEnterpriseDtoMapper enterprisePostRequestDataToEnterpriseDtoMapper;
    private final EnterprisePutRequestDataToEnterpriseDtoMapper enterprisePutRequestDataToEnterpriseDtoMapper;

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Collection<EnterpriseResponseData>> get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(
                enterpriseService.getEnterpriseForManager(username)
                        .stream()
                        .map(enterpriseDtoToEnterpriseResponseDataMapper::map)
                        .toList()
        );
    }

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<EnterpriseResponseData> post(EnterprisePostRequestData enterprise) {
        var responseData = enterpriseService.add(enterprisePostRequestDataToEnterpriseDtoMapper.map(enterprise));
        return ResponseEntity.status(HttpStatus.CREATED).body(enterpriseDtoToEnterpriseResponseDataMapper.map(responseData));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Void> delete(Integer id){
        Optional<EnterpriseDto> enterpriseDto = enterpriseService.remove(id);
        if (enterpriseDto.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<EnterpriseResponseData> put(Integer id, EnterprisePutRequestData enterprisePutRequestData) {
        Optional<EnterpriseDto> updateEnterpriseDto =
                enterpriseService.update(id, enterprisePutRequestDataToEnterpriseDtoMapper.map(enterprisePutRequestData));
        return updateEnterpriseDto
                .map(dto -> ResponseEntity.ok(enterpriseDtoToEnterpriseResponseDataMapper.map(dto)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
