package com.venikovdi.carpark.entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;

import java.util.Collection;

@Getter
@Setter
@Entity
@Table(schema = "carpark", name = "tbl_role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<Manager> managers;

    public Role() {
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
