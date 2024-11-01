package org.example.itog.Controllers;

import org.example.itog.Models.Passenger;
import org.example.itog.Repositories.PassengerRepository; // Замените на реальный репозиторий
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/passenger")
public class PassengerViewController {

    private final PassengerRepository passengerService;

    @Autowired
    public PassengerViewController(PassengerRepository passengerService) {
        this.passengerService = passengerService;
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping
    public String getAllPassengers(Model model) {
        model.addAttribute("passengers", passengerService.findAll());
        return "passengers/list";  // указывает на passengers/list.html
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("passenger", new Passenger());
        return "passengers/form";  // указывает на passengers/form.html
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping
    public String createPassenger(@ModelAttribute Passenger passenger, RedirectAttributes redirectAttributes) {
        passengerService.save(passenger);
        redirectAttributes.addFlashAttribute("success", "Пассажир успешно добавлен");
        return "redirect:/passenger";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return passengerService.findById(id).map(passenger -> {
            model.addAttribute("passenger", passenger);
            return "passengers/form"; // указывает на passengers/form.html
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("error", "Пассажир не найден");
            return "redirect:/passenger";
        });
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update/{id}")
    public String updatePassenger(@PathVariable Long id, @ModelAttribute Passenger passengerDetails, RedirectAttributes redirectAttributes) {
        passengerService.findById(id).ifPresent(passenger -> {
            passenger.setFirstName(passengerDetails.getFirstName());
            passenger.setLastName(passengerDetails.getLastName());
            passenger.setEmail(passengerDetails.getEmail());
            passenger.setPhoneNumber(passengerDetails.getPhoneNumber());
            passengerService.save(passenger);
            redirectAttributes.addFlashAttribute("success", "Пассажир успешно обновлен");
        });
        return "redirect:/passenger";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deletePassenger(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (passengerService.findById(id).isPresent()) {
            passengerService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Пассажир успешно удален");
        } else {
            redirectAttributes.addFlashAttribute("error", "Пассажир не найден");
        }
        return "redirect:/passenger";
    }
}
