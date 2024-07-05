package com.venikovdi.carpark.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.venikovdi.carpark.data.dto.VehicleDto;
import com.venikovdi.carpark.service.VehicleService;

@PageTitle("Vehicle List")
@Route(value = VehicleListView.ROUTE)
public class VehicleListView extends VerticalLayout {

    public static final String ROUTE = "list/vehicle-list";
    private final VehicleService vehicleService;

    public VehicleListView(VehicleService vehicleService) {
        this.vehicleService = vehicleService;

        createGrid();

        setMargin(false);
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.STRETCH);
        setSizeFull();
    }

    private void createGrid() {
        Grid<VehicleDto> grid = getGrid();
        grid.setSizeFull();

        HorizontalLayout gridWrapper = new HorizontalLayout(grid);
        gridWrapper.setMargin(false);
        gridWrapper.setPadding(false);
        gridWrapper.setSpacing(false);
        gridWrapper.setFlexGrow(1, grid);
        gridWrapper.setSizeFull();

        add(gridWrapper);
    }

    private Grid<VehicleDto> getGrid() {
        Grid<VehicleDto> grid = new Grid<>();
        grid.setItems(vehicleService.getAll());
        grid.addColumn(VehicleDto::id).setHeader("Vehicle id");
        grid.addColumn(VehicleDto::brandId).setHeader("Brand id");
        grid.addColumn(VehicleDto::price).setHeader("Vehicle price");
        grid.addColumn(VehicleDto::mileage).setHeader("Vehicle mileage");
        grid.addColumn(VehicleDto::releaseYear).setHeader("Vehicle release year");
        grid.addColumn(VehicleDto::color).setHeader("Vehicle color");
        grid.addColumn(VehicleDto::number).setHeader("Vehicle number");
        return grid;
    }
}
