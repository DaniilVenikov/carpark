package com.venikovdi.carpark.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.venikovdi.carpark.entity.Brand;
import com.venikovdi.carpark.entity.Vehicle;
import com.venikovdi.carpark.repository.VehicleRepository;

@Route("/vehicle")
public class VehicleView extends VerticalLayout implements HasUrlParameter<Integer> {

    private final VehicleRepository repo;
    private final Grid<Vehicle> grid;

    public VehicleView(VehicleRepository repo, VehicleEditor editor) {
        this.repo = repo;
        this.grid = new Grid<>(Vehicle.class);
        Button addNewBtn = new Button("New vehicle", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(addNewBtn);

        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id", "price", "mileage", "releaseYear", "color", "number");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        grid.addColumn(vehicle -> {
            Brand brand = vehicle.getBrand();
            return brand != null ? brand.getTitle() : "";
        }).setHeader("Brand");

        // Hook logic to components

        grid.asSingleSelect().addValueChangeListener(e -> editor.editCustomer(e.getValue()));
        addNewBtn.addClickListener(e -> editor.editCustomer(new Vehicle()));
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listCustomers(null);
        });

        grid.addComponentColumn(vehicle -> {
            Button viewVehiclesButton = new Button("View Drives");
            viewVehiclesButton.addClickListener(event -> UI.getCurrent().navigate(DriveView.class, vehicle.getId()));
            return viewVehiclesButton;
        }).setHeader("Drives");

        // Initialize listing without filtering
        listCustomers(null);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer enterpriseId) {
        if (enterpriseId != null) {
            listCustomers(enterpriseId);
        }
    }

    private void listCustomers(Integer enterpriseId) {
        if (enterpriseId != null) {
            grid.setItems(repo.findByEnterpriseId(enterpriseId));
        } else {
            grid.setItems(repo.findAll());
        }
    }
}
