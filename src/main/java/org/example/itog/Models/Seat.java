package org.example.itog.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Seat number cannot be blank")
    private String seatNumber;

    @NotBlank(message = "Seat class cannot be blank")
    @Pattern(regexp = "Economy|Business|First", message = "Seat class must be Economy, Business, or First")
    private String seatClass;

    @NotNull(message = "Flight cannot be null")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    private boolean isAvailable;

    public Seat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Seat number cannot be blank") String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(@NotBlank(message = "Seat number cannot be blank") String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public @NotBlank(message = "Seat class cannot be blank") @Pattern(regexp = "Economy|Business|First", message = "Seat class must be Economy, Business, or First") String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(@NotBlank(message = "Seat class cannot be blank") @Pattern(regexp = "Economy|Business|First", message = "Seat class must be Economy, Business, or First") String seatClass) {
        this.seatClass = seatClass;
    }

    public @NotNull(message = "Flight cannot be null") Flight getFlight() {
        return flight;
    }

    public void setFlight(@NotNull(message = "Flight cannot be null") Flight flight) {
        this.flight = flight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Seat(Long id, String seatNumber, String seatClass, Flight flight, boolean isAvailable) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.flight = flight;
        this.isAvailable = isAvailable;
    }
}
