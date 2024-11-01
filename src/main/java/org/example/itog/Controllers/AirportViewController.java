package org.example.itog.Controllers;


import org.example.itog.Models.Airport;
import org.example.itog.Repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/airport")
public class AirportViewController {

    private final AirportRepository airportService;

    @Autowired
    public AirportViewController(AirportRepository airportService) {
        this.airportService = airportService;
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN', 'CAHIER')")
    @GetMapping
    public String getAllAirports(Model model) {
        model.addAttribute("airports", airportService.findAll());
        return "airports/list";  // указывает на airports/list.html
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("airport", new Airport());
        return "airports/form";  // указывает на airports/form.html
    }
    @PreAuthorize("hasAuthority('ADMIN')")

    @PostMapping
    public String createAirport(@ModelAttribute Airport airport, RedirectAttributes redirectAttributes) {
        airportService.save(airport);
        redirectAttributes.addFlashAttribute("success", "Аэропорт успешно добавлен");
        return "redirect:/airport";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return airportService.findById(id).map(airport -> {
            model.addAttribute("airport", airport);
            return "form";  // изменено на "form", если форма общая
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("error", "Аэропорт не найден");
            return "redirect:/airport";
        });
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update/{id}")
    public String updateAirport(@PathVariable Long id, @ModelAttribute Airport airportDetails, RedirectAttributes redirectAttributes) {
        airportService.findById(id).ifPresent(airport -> {
            airport.setCode(airportDetails.getCode());
            airport.setName(airportDetails.getName());
            airport.setCity(airportDetails.getCity());
            airport.setCountry(airportDetails.getCountry());
            airportService.save(airport);
            redirectAttributes.addFlashAttribute("success", "Аэропорт успешно обновлен");
        });
        return "redirect:/airport";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteAirport(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (airportService.findById(id).isPresent()) {
            airportService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Аэропорт успешно удален");
        } else {
            redirectAttributes.addFlashAttribute("error", "Аэропорт не найден");
        }
        return "redirect:/airport";
    }

}
