package org.example.itog.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Luggage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Ticket cannot be null")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @PositiveOrZero(message = "Weight must be positive or zero")
    private double weight;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Ticket cannot be null") Ticket getTicket() {
        return ticket;
    }

    public void setTicket(@NotNull(message = "Ticket cannot be null") Ticket ticket) {
        this.ticket = ticket;
    }

    @PositiveOrZero(message = "Weight must be positive or zero")
    public double getWeight() {
        return weight;
    }

    public void setWeight(@PositiveOrZero(message = "Weight must be positive or zero") double weight) {
        this.weight = weight;
    }

    public @Size(max = 255, message = "Description must be less than 255 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 255, message = "Description must be less than 255 characters") String description) {
        this.description = description;
    }

    public Luggage(Long id, Ticket ticket, double weight, String description) {
        this.id = id;
        this.ticket = ticket;
        this.weight = weight;
        this.description = description;
    }

    public Luggage() {
    }
}
