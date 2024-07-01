package com.venikovdi.carpark.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.venikovdi.carpark.entity.Vehicle;
import com.venikovdi.carpark.repository.VehicleRepository;

@PageTitle("Basic List")
@Route(value = BasicListView.ROUTE)
public class BasicListView extends VerticalLayout {

    public static final String ROUTE = "list/basic-list";

    private final VehicleRepository vehicleRepository;

    public BasicListView(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;

        createGrid();

        setMargin(false);
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.STRETCH);
        setSizeFull();
    }

    private void createGrid() {
        Grid<Vehicle> grid = getGrid();
        grid.setSizeFull();

        HorizontalLayout gridWrapper = new HorizontalLayout(grid);
        gridWrapper.setMargin(false);
        gridWrapper.setPadding(false);
        gridWrapper.setSpacing(false);
        gridWrapper.setFlexGrow(1, grid);
        gridWrapper.setSizeFull();

        add(gridWrapper);
    }

    private Grid<Vehicle> getGrid() {
        Grid<Vehicle> grid = new Grid<>();
        grid.setItems(vehicleRepository.findAll());
        grid.addColumn(Vehicle::getPrice).setHeader("Vehicle price");
        grid.addColumn(Vehicle::getMileage).setHeader("Vehicle mileage");
        grid.addColumn(Vehicle::getReleaseYear).setHeader("Vehicle release year");
        grid.addColumn(Vehicle::getColor).setHeader("Vehicle color");
        return grid;
    }
}
