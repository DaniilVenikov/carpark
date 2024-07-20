package com.venikovdi.carpark.controller;

import com.venikovdi.carpark.api.EnterpriseApi;
import com.venikovdi.carpark.api.data.EnterpriseResponseData;
import com.venikovdi.carpark.mapper.EnterpriseDtoToEnterpriseResponseDataMapper;
import com.venikovdi.carpark.service.EnterpriseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class EnterpriseController implements EnterpriseApi {
    private final EnterpriseService enterpriseService;
    private final EnterpriseDtoToEnterpriseResponseDataMapper enterpriseDtoToEnterpriseResponseDataMapper;

    @Override
    public ResponseEntity<Collection<EnterpriseResponseData>> get() {
        return ResponseEntity.ok(
                enterpriseService.getAllEnterprises()
                        .stream()
                        .map(enterpriseDtoToEnterpriseResponseDataMapper::map)
                        .toList()
        );
    }
}
