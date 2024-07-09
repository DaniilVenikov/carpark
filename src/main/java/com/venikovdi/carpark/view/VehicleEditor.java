package com.venikovdi.carpark.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.venikovdi.carpark.entity.Brand;
import com.venikovdi.carpark.entity.Vehicle;
import com.venikovdi.carpark.repository.BrandRepository;
import com.venikovdi.carpark.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class VehicleEditor extends VerticalLayout implements KeyNotifier {

    private final VehicleRepository vehicleRepository;

    /**
     * The currently edited customer
     */
    private Vehicle vehicle;

    /* Fields to edit properties in Customer entity */
    TextField color = new TextField("Color");
    TextField number = new TextField("Number");
    IntegerField price = new IntegerField("Price");
    IntegerField mileage = new IntegerField("Mileage");
    IntegerField releaseYear = new IntegerField("Release Year");
    ComboBox<Brand> brand = new ComboBox<>("Brand");


    /* Action buttons */
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Vehicle> binder = new Binder<>(Vehicle.class);
    private ChangeHandler changeHandler;

    @Autowired
    public VehicleEditor(VehicleRepository vehicleRepository, BrandRepository brandRepository) {
        this.vehicleRepository = vehicleRepository;

        brand.setItems(brandRepository.findAll());
        brand.setItemLabelGenerator(Brand::getTitle);

        add(price, mileage, color, number, releaseYear, brand, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editCustomer(vehicle));
        setVisible(false);
    }

    void delete() {
        vehicleRepository.delete(vehicle);
        changeHandler.onChange();
    }

    void save() {
        vehicleRepository.save(vehicle);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editCustomer(Vehicle vehicle) {
        if (vehicle == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = vehicle.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            // In a more complex app, you might want to load
            // the entity/DTO with lazy loaded relations for editing
            this.vehicle = vehicleRepository.findById(vehicle.getId()).get();
        }
        else {
            this.vehicle = vehicle;
        }
        cancel.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(this.vehicle);

        releaseYear.setReadOnly(persisted);
        brand.setReadOnly(persisted);

        setVisible(true);

        // Focus first name initially
        color.focus();
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        this.changeHandler = changeHandler;
    }

}
