package org.example.itog.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Booking cannot be null")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @NotBlank(message = "Seat number cannot be blank")
    private String seatNumber;

    @Positive(message = "Price must be positive")
    private double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Booking cannot be null") Booking getBooking() {
        return booking;
    }

    public void setBooking(@NotNull(message = "Booking cannot be null") Booking booking) {
        this.booking = booking;
    }

    public @NotBlank(message = "Seat number cannot be blank") String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(@NotBlank(message = "Seat number cannot be blank") String seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Positive(message = "Price must be positive")
    public double getPrice() {
        return price;
    }

    public void setPrice(@Positive(message = "Price must be positive") double price) {
        this.price = price;
    }

    public Ticket() {
    }

    public Ticket(Long id, Booking booking, String seatNumber, double price) {
        this.id = id;
        this.booking = booking;
        this.seatNumber = seatNumber;
        this.price = price;
    }
}
