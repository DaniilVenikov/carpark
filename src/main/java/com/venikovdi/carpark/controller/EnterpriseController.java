package com.venikovdi.carpark.controller;

import com.venikovdi.carpark.api.EnterpriseApi;
import com.venikovdi.carpark.api.data.EnterpriseResponseData;
import com.venikovdi.carpark.mapper.EnterpriseDtoToEnterpriseResponseDataMapper;
import com.venikovdi.carpark.service.EnterpriseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class EnterpriseController implements EnterpriseApi {
    private final EnterpriseService enterpriseService;
    private final EnterpriseDtoToEnterpriseResponseDataMapper enterpriseDtoToEnterpriseResponseDataMapper;

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
}
