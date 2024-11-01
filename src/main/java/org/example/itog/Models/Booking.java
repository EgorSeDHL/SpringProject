package org.example.itog.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PastOrPresent(message = "Booking date cannot be in the future")
    private LocalDateTime bookingDate;

    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp = "Confirmed|Canceled|Pending", message = "Status must be Confirmed, Canceled, or Pending")
    private String status;

    @NotNull(message = "Flight cannot be null")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @NotNull(message = "Passenger cannot be null")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    public Booking() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @PastOrPresent(message = "Booking date cannot be in the future") LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(@PastOrPresent(message = "Booking date cannot be in the future") LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public @NotBlank(message = "Status cannot be blank") @Pattern(regexp = "Confirmed|Canceled|Pending", message = "Status must be Confirmed, Canceled, or Pending") String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank(message = "Status cannot be blank") @Pattern(regexp = "Confirmed|Canceled|Pending", message = "Status must be Confirmed, Canceled, or Pending") String status) {
        this.status = status;
    }

    public @NotNull(message = "Flight cannot be null") Flight getFlight() {
        return flight;
    }

    public void setFlight(@NotNull(message = "Flight cannot be null") Flight flight) {
        this.flight = flight;
    }

    public @NotNull(message = "Passenger cannot be null") Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(@NotNull(message = "Passenger cannot be null") Passenger passenger) {
        this.passenger = passenger;
    }

    public Booking(Long id, LocalDateTime bookingDate, String status, Flight flight, Passenger passenger) {
        this.id = id;
        this.bookingDate = bookingDate;
        this.status = status;
        this.flight = flight;
        this.passenger = passenger;
    }
}
