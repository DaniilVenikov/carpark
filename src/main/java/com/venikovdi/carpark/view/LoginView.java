package com.venikovdi.carpark.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "login", layout = MainLayout.class)
@PageTitle("Login")
public class LoginView extends VerticalLayout {

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginView() {
        setClassName("login-view");

        TextField usernameField = new TextField("Username");
        usernameField.setWidth("100%");

        PasswordField passwordField = new PasswordField("Password");
        passwordField.setWidth("100%");

        Button loginButton = new Button("Login");
        loginButton.setWidth("100%");
        loginButton.addClassName("btn-primary");

        loginButton.addClickListener(event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();

            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);

                getUI().ifPresent(ui -> ui.navigate("enterprise"));

            } catch (AuthenticationException e) {
                Notification.show("Invalid username or password", 3000, Notification.Position.MIDDLE);
            }
        });

        VerticalLayout formLayout = new VerticalLayout(usernameField, passwordField, loginButton);
        formLayout.setSpacing(true);
        formLayout.setAlignItems(Alignment.STRETCH);
        formLayout.setWidth("300px");

        VerticalLayout container = new VerticalLayout(formLayout);
        container.setSizeFull();
        container.setJustifyContentMode(JustifyContentMode.CENTER);
        container.setAlignItems(Alignment.CENTER);

        add(container);
    }
}