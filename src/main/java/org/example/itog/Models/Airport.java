package org.example.itog.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Airport code cannot be blank")
    @Size(min = 3, max = 3, message = "Airport code must be exactly 3 characters")
    private String code;

    @NotBlank(message = "Airport name cannot be blank")
    @Size(max = 100, message = "Airport name must be less than 100 characters")
    private String name;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "Country cannot be blank")
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Airport code cannot be blank") @Size(min = 3, max = 3, message = "Airport code must be exactly 3 characters") String getCode() {
        return code;
    }

    public void setCode(@NotBlank(message = "Airport code cannot be blank") @Size(min = 3, max = 3, message = "Airport code must be exactly 3 characters") String code) {
        this.code = code;
    }

    public @NotBlank(message = "Airport name cannot be blank") @Size(max = 100, message = "Airport name must be less than 100 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Airport name cannot be blank") @Size(max = 100, message = "Airport name must be less than 100 characters") String name) {
        this.name = name;
    }

    public @NotBlank(message = "City cannot be blank") String getCity() {
        return city;
    }

    public void setCity(@NotBlank(message = "City cannot be blank") String city) {
        this.city = city;
    }

    public @NotBlank(message = "Country cannot be blank") String getCountry() {
        return country;
    }

    public void setCountry(@NotBlank(message = "Country cannot be blank") String country) {
        this.country = country;
    }

    public Airport(Long id, String code, String name, String city, String country) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.city = city;
        this.country = country;
    }

    public Airport() {
    }

    // Getters and Setters
}


