package com.venikovdi.carpark.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.venikovdi.carpark.data.dto.BrandDto;
import com.venikovdi.carpark.service.BrandService;

@PageTitle("Brand List")
@Route(value = BrandListView.ROUTE)
public class BrandListView extends VerticalLayout {
    public static final String ROUTE = "list/brand-list";

    private final BrandService brandService;

    public BrandListView(BrandService brandService) {
        this.brandService = brandService;

        createGrid();

        setMargin(false);
        setPadding(false);
        setSpacing(false);
        setAlignItems(FlexComponent.Alignment.STRETCH);
        setSizeFull();
    }

    private void createGrid() {
        Grid<BrandDto> grid = getGrid();
        grid.setSizeFull();

        HorizontalLayout gridWrapper = new HorizontalLayout(grid);
        gridWrapper.setMargin(false);
        gridWrapper.setPadding(false);
        gridWrapper.setSpacing(false);
        gridWrapper.setFlexGrow(1, grid);
        gridWrapper.setSizeFull();

        add(gridWrapper);
    }

    private Grid<BrandDto> getGrid() {
        Grid<BrandDto> grid = new Grid<>();
        grid.setItems(brandService.getAll());
        grid.addColumn(BrandDto::id).setHeader("Brand id");
        grid.addColumn(BrandDto::title).setHeader("Brand title");
        grid.addColumn(BrandDto::type).setHeader("Brand type");
        grid.addColumn(BrandDto::volumeTank).setHeader("Brand volume tank");
        grid.addColumn(BrandDto::liftingCapacity).setHeader("Brand lifting capacity");
        grid.addColumn(BrandDto::numberSeats).setHeader("Brand number seats");
        return grid;
    }
}
