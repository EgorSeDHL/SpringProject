package org.example.itog.Controllers;


import org.example.itog.Models.Booking;
import org.example.itog.Repositories.BookingRepository; // Замените на реальный репозиторий
import org.example.itog.Repositories.FlightRepository;
import org.example.itog.Repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/booking")
public class BookingViewController {

    private final BookingRepository bookingService;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    @Autowired
    public BookingViewController(BookingRepository bookingService, FlightRepository flightRepository, PassengerRepository passengerRepository) {
        this.bookingService = bookingService;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping
    public String getAllBookings(Model model) {
        model.addAttribute("bookings", bookingService.findAll());
        return "bookings/list";  // указывает на bookings/list.html
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("flights", flightRepository.findAll());
        model.addAttribute("passengers", passengerRepository.findAll());
        return "bookings/form";  // указывает на bookings/form.html
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String createBooking(@ModelAttribute Booking booking, RedirectAttributes redirectAttributes) {
        bookingService.save(booking);
        redirectAttributes.addFlashAttribute("success", "Бронирование успешно добавлено");
        return "redirect:/booking";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return bookingService.findById(id).map(booking -> {
            model.addAttribute("booking", booking);
            model.addAttribute("flights", flightRepository.findAll());
            model.addAttribute("passengers", passengerRepository.findAll());
            return "bookings/form"; // указывает на bookings/form.html
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("error", "Бронирование не найдено");
            return "redirect:/booking";
        });
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update/{id}")
    public String updateBooking(@PathVariable Long id, @ModelAttribute Booking bookingDetails, RedirectAttributes redirectAttributes) {
        bookingService.findById(id).ifPresent(booking -> {
            booking.setBookingDate(bookingDetails.getBookingDate());
            booking.setStatus(bookingDetails.getStatus());
            booking.setFlight(bookingDetails.getFlight());
            booking.setPassenger(bookingDetails.getPassenger());
            bookingService.save(booking);
            redirectAttributes.addFlashAttribute("success", "Бронирование успешно обновлено");
        });
        return "redirect:/booking";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (bookingService.findById(id).isPresent()) {
            bookingService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Бронирование успешно удалено");
        } else {
            redirectAttributes.addFlashAttribute("error", "Бронирование не найдено");
        }
        return "redirect:/booking";
    }
}
