package com.venikovdi.carpark.view;

import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.RouterLayout;

@StyleSheet("frontend://bower_components/bootstrap/dist/css/bootstrap.min.css")
@JavaScript("frontend://bower_components/bootstrap/dist/js/bootstrap.min.js")
public class MainLayout extends Div implements RouterLayout{
        public MainLayout() {
            // Настройки компонента
        }
}
