package org.example.itog.Controllers;

import org.example.itog.Models.Ticket;
import org.example.itog.Repositories.BookingRepository;
import org.example.itog.Repositories.TicketRepository; // Замените на реальный репозиторий
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ticket")
public class TicketViewController {

    private final TicketRepository ticketService;
    private final BookingRepository bookingRepository;

    @Autowired
    public TicketViewController(TicketRepository ticketService, BookingRepository bookingRepository) {
        this.ticketService = ticketService;
        this.bookingRepository = bookingRepository;
    }
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'CASHIER')")
    @GetMapping
    public String getAllTickets(Model model) {
        model.addAttribute("bookings", bookingRepository.findAll());
        model.addAttribute("tickets", ticketService.findAll());
        return "tickets/list";  // указывает на tickets/list.html
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CASHIER')")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("bookings", bookingRepository.findAll());
        return "tickets/form";  // указывает на tickets/form.html
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CASHIER')")
    @PostMapping
    public String createTicket(@ModelAttribute Ticket ticket, RedirectAttributes redirectAttributes) {
        ticketService.save(ticket);
        redirectAttributes.addFlashAttribute("success", "Билет успешно добавлен");
        return "redirect:/ticket";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CASHIER')")
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return ticketService.findById(id).map(ticket -> {
            model.addAttribute("ticket", ticket);
            model.addAttribute("bookings", bookingRepository.findAll());

            return "tickets/form"; // указывает на tickets/form.html
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("error", "Билет не найден");
            return "redirect:/ticket";
        });
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CASHIER')")
    @PostMapping("/update/{id}")
    public String updateTicket(@PathVariable Long id, @ModelAttribute Ticket ticketDetails, RedirectAttributes redirectAttributes) {
        ticketService.findById(id).ifPresent(ticket -> {
            ticket.setPrice(ticketDetails.getPrice());
            ticket.setBooking(ticketDetails.getBooking());
            ticket.setSeatNumber(ticketDetails.getSeatNumber());
            ticketService.save(ticket);
            redirectAttributes.addFlashAttribute("success", "Билет успешно обновлен");
        });
        return "redirect:/ticket";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CASHIER')")
    @GetMapping("/delete/{id}")
    public String deleteTicket(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (ticketService.findById(id).isPresent()) {
            ticketService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Билет успешно удален");
        } else {
            redirectAttributes.addFlashAttribute("error", "Билет не найден");
        }
        return "redirect:/ticket";
    }
}
