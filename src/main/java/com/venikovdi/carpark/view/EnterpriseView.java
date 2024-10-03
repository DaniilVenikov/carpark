package com.venikovdi.carpark.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.venikovdi.carpark.data.dto.EnterpriseDto;
import com.venikovdi.carpark.service.EnterpriseService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

@Route("/enterprise")
public class EnterpriseView extends VerticalLayout {

    private final EnterpriseService enterpriseService;

    public EnterpriseView(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @PostConstruct
    private void initView() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Collection<EnterpriseDto> enterprises = enterpriseService.getEnterpriseForManager(username);

            Grid<EnterpriseDto> grid = new Grid<>(EnterpriseDto.class);
            grid.setItems(enterprises);
            grid.setColumns("id", "title", "city");

            grid.addComponentColumn(enterprise -> {
                Button viewVehiclesButton = new Button("View Vehicles");
                viewVehiclesButton.addClickListener(event -> UI.getCurrent().navigate(VehicleView.class, enterprise.id()));
                return viewVehiclesButton;
            }).setHeader("Vehicles");

            add(grid);
        } else {
            add(new Text("You are not authenticated. Please log in."));
        }
    }
}
