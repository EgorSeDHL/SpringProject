package org.example.itog.Models;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;

@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "First name cannot be blank") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First name cannot be blank") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Last name cannot be blank") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Last name cannot be blank") String lastName) {
        this.lastName = lastName;
    }

    public @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Passenger(Long id, String firstName, String lastName, String email, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Passenger() {
    }
}

