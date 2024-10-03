package com.venikovdi.carpark.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.venikovdi.carpark.entity.Drive;
import com.venikovdi.carpark.repository.DriveRepository;

import java.time.format.DateTimeFormatter;

import com.vaadin.flow.component.combobox.ComboBox;
import java.time.LocalDate;
import java.util.List;

@Route("/drive")
public class DriveView extends VerticalLayout implements HasUrlParameter<Integer> {

    private final DriveRepository driveRepository;
    private final Grid<Drive> grid;
    private Integer currentVehicleId = null;  // Хранение текущего vehicleId

    public DriveView(DriveRepository driveRepository) {
        this.driveRepository = driveRepository;
        this.grid = new Grid<>(Drive.class);
        ComboBox<String> dateFilterComboBox = new ComboBox<>("Filter by Date");

        grid.setHeight("300px");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        grid.setColumns("id");
        grid.getColumnByKey("id").setHeader("ID").setWidth("100px").setFlexGrow(0);

        grid.addColumn(drive -> drive.getStart().format(formatter)).setHeader("Start Time");
        grid.addColumn(drive -> drive.getEnd().format(formatter)).setHeader("End Time");

        grid.addComponentColumn(drive -> {
            Button routeButton = new Button("View Route");
            routeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            routeButton.addClickListener(e -> routeButton.getUI().ifPresent(ui -> ui.navigate(RouteView.class, drive.getId().intValue())));
            return routeButton;
        }).setHeader("Route");


        // Заполняем фильтр по дате
        dateFilterComboBox.setItems("Last Week", "Last Month");
        dateFilterComboBox.addValueChangeListener(event -> filterByDate(event.getValue()));

        add(dateFilterComboBox, grid);

        // Initialize listing without filtering
        listDrive(null);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer vehicleId) {
        this.currentVehicleId = vehicleId;  // Сохраняем vehicleId
        listDrive(vehicleId);
    }

    private void listDrive(Integer vehicleId) {
        if (vehicleId != null) {
            grid.setItems(driveRepository.findAllByVehicleId(vehicleId));
        } else {
            grid.setItems(driveRepository.findAll());
        }
    }

    // Метод для фильтрации поездок по выбранному периоду
    private void filterByDate(String filterOption) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = null;

        if ("Last Week".equals(filterOption)) {
            startDate = now.minusWeeks(1);
        } else if ("Last Month".equals(filterOption)) {
            startDate = now.minusMonths(1);
        }

        if (startDate != null) {
            if (currentVehicleId != null) {
                // Если vehicleId передан, фильтруем и по нему
                List<Drive> filteredDrives = driveRepository.findAllByVehicleIdAndStartAfter(currentVehicleId, startDate.atStartOfDay());
                grid.setItems(filteredDrives);
            } else {
                // Если vehicleId не передан, фильтруем только по дате
                List<Drive> filteredDrives = driveRepository.findAllByStartAfter(startDate.atStartOfDay());
                grid.setItems(filteredDrives);
            }
        } else {
            listDrive(currentVehicleId);  // Если фильтр не выбран, отображаем все записи
        }
    }
}
