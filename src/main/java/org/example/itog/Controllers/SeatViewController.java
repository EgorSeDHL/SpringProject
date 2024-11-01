package org.example.itog.Controllers;

import org.example.itog.Models.Seat;
import org.example.itog.Repositories.FlightRepository;
import org.example.itog.Repositories.SeatRepository; // Замените на реальный репозиторий
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/seat")
public class SeatViewController {

    private final SeatRepository seatService;
    private final FlightRepository flightRepository;

    @Autowired
    public SeatViewController(SeatRepository seatService, FlightRepository flightRepository) {
        this.seatService = seatService;
        this.flightRepository = flightRepository;
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping
    public String getAllSeats(Model model) {
        model.addAttribute("seats", seatService.findAll());
        return "seats/list";  // указывает на seats/list.html
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("seat", new Seat());
        model.addAttribute("flights", flightRepository.findAll());
        return "seats/form";  // указывает на seats/form.html
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String createSeat(@ModelAttribute Seat seat, RedirectAttributes redirectAttributes) {
        seatService.save(seat);
        redirectAttributes.addFlashAttribute("success", "Место успешно добавлено");
        return "redirect:/seat";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return seatService.findById(id).map(seat -> {
            model.addAttribute("seat", seat);
            model.addAttribute("flights", flightRepository.findAll());

            return "seats/form"; // указывает на seats/form.html
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("error", "Место не найдено");
            return "redirect:/seat";
        });
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update/{id}")
    public String updateSeat(@PathVariable Long id, @ModelAttribute Seat seatDetails, RedirectAttributes redirectAttributes) {
        seatService.findById(id).ifPresent(seat -> {
            seat.setSeatNumber(seatDetails.getSeatNumber());
            seat.setSeatClass(seatDetails.getSeatClass());
            seat.setFlight(seatDetails.getFlight());
            seat.setAvailable(seatDetails.isAvailable());
            seatService.save(seat);
            redirectAttributes.addFlashAttribute("success", "Место успешно обновлено");
        });
        return "redirect:/seat";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteSeat(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (seatService.findById(id).isPresent()) {
            seatService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Место успешно удалено");
        } else {
            redirectAttributes.addFlashAttribute("error", "Место не найдено");
        }
        return "redirect:/seat";
    }
}
