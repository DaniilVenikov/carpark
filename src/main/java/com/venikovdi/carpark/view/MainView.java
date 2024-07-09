package com.venikovdi.carpark.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.venikovdi.carpark.entity.Brand;
import com.venikovdi.carpark.entity.Vehicle;
import com.venikovdi.carpark.repository.VehicleRepository;

@Route
public class MainView extends VerticalLayout {

    private final VehicleRepository repo;

    final Grid<Vehicle> grid;

    public MainView(VehicleRepository repo, VehicleEditor editor) {
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


        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> editor.editCustomer(e.getValue()));

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editCustomer(new Vehicle()));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listCustomers();
        });

        // Initialize listing
        listCustomers();
    }
    private void listCustomers() {
            grid.setItems(repo.findAll());
    }
}
