package org.example.itog.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Booking cannot be null")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Positive(message = "Amount must be positive")
    private double amount;

    @PastOrPresent(message = "Payment date cannot be in the future")
    private LocalDateTime paymentDate;

    @NotBlank(message = "Payment method cannot be blank")
    @Pattern(regexp = "Credit Card|PayPal|Cash", message = "Payment method must be Credit Card, PayPal, or Cash")
    private String paymentMethod;

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

    @Positive(message = "Amount must be positive")
    public double getAmount() {
        return amount;
    }

    public void setAmount(@Positive(message = "Amount must be positive") double amount) {
        this.amount = amount;
    }

    public @PastOrPresent(message = "Payment date cannot be in the future") LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(@PastOrPresent(message = "Payment date cannot be in the future") LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public @NotBlank(message = "Payment method cannot be blank") @Pattern(regexp = "Credit Card|PayPal|Cash", message = "Payment method must be Credit Card, PayPal, or Cash") String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(@NotBlank(message = "Payment method cannot be blank") @Pattern(regexp = "Credit Card|PayPal|Cash", message = "Payment method must be Credit Card, PayPal, or Cash") String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Payment(Long id, Booking booking, double amount, LocalDateTime paymentDate, String paymentMethod) {
        this.id = id;
        this.booking = booking;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }

    public Payment() {
    }
}
