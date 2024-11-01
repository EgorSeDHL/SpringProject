package org.example.itog.Models;



import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import java.time.LocalDateTime;
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Flight number cannot be blank")
    @Size(max = 10, message = "Flight number must be less than 10 characters")
    private String flightNumber;

    @NotNull(message = "Departure airport cannot be null")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "departure_airport_id")
    private Airport departureAirport;

    @NotNull(message = "Arrival airport cannot be null")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "arrival_airport_id")
    private Airport arrivalAirport;

    @Future(message = "Departure time must be in the future")
    private LocalDateTime departureTime;

    @Future(message = "Arrival time must be in the future")
    private LocalDateTime arrivalTime;

    @Min(value = 1, message = "There must be at least one available seat")
    private int availableSeats;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Flight number cannot be blank") @Size(max = 10, message = "Flight number must be less than 10 characters") String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(@NotBlank(message = "Flight number cannot be blank") @Size(max = 10, message = "Flight number must be less than 10 characters") String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public @NotNull(message = "Departure airport cannot be null") Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(@NotNull(message = "Departure airport cannot be null") Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public @NotNull(message = "Arrival airport cannot be null") Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(@NotNull(message = "Arrival airport cannot be null") Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public @Future(message = "Departure time must be in the future") LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(@Future(message = "Departure time must be in the future") LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public @Future(message = "Arrival time must be in the future") LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(@Future(message = "Arrival time must be in the future") LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Min(value = 1, message = "There must be at least one available seat")
    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(@Min(value = 1, message = "There must be at least one available seat") int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Flight(Long id, String flightNumber, Airport departureAirport, Airport arrivalAirport, LocalDateTime departureTime, LocalDateTime arrivalTime, int availableSeats) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
    }

    public Flight() {
    }
}