package org.example.itog.Controllers;

import org.example.itog.Models.Payment;
import org.example.itog.Repositories.BookingRepository;
import org.example.itog.Repositories.PaymentRepository; // Замените на реальный репозиторий
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/payment")
public class PaymentViewController {

    private final PaymentRepository paymentService;
    private final BookingRepository bookingRepository;

    @Autowired
    public PaymentViewController(PaymentRepository paymentService, BookingRepository bookingRepository) {
        this.paymentService = paymentService;
        this.bookingRepository = bookingRepository;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String getAllPayments(Model model) {
        model.addAttribute("payments", paymentService.findAll());
        return "payments/list";  // указывает на payments/list.html
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("bookings", bookingRepository.findAll());
        model.addAttribute("payment", new Payment());
        return "payments/form";  // указывает на payments/form.html
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String createPayment(@ModelAttribute Payment payment, RedirectAttributes redirectAttributes) {
        paymentService.save(payment);
        redirectAttributes.addFlashAttribute("success", "Платеж успешно добавлен");
        return "redirect:/payment";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return paymentService.findById(id).map(payment -> {
            model.addAttribute("bookings", bookingRepository.findAll());

            model.addAttribute("payment", payment);
            return "payments/form"; // указывает на payments/form.html
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("error", "Платеж не найден");
            return "redirect:/payment";
        });
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update/{id}")
    public String updatePayment(@PathVariable Long id, @ModelAttribute Payment paymentDetails, RedirectAttributes redirectAttributes) {
        paymentService.findById(id).ifPresent(payment -> {
            payment.setAmount(paymentDetails.getAmount());
            payment.setPaymentDate(paymentDetails.getPaymentDate());
            payment.setBooking(paymentDetails.getBooking());
            paymentService.save(payment);
            redirectAttributes.addFlashAttribute("success", "Платеж успешно обновлен");
        });
        return "redirect:/payment";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (paymentService.findById(id).isPresent()) {
            paymentService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Платеж успешно удален");
        } else {
            redirectAttributes.addFlashAttribute("error", "Платеж не найден");
        }
        return "redirect:/payment";
    }
}
