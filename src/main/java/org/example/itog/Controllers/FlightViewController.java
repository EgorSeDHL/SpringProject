package org.example.itog.Controllers;

import org.example.itog.Models.Airport;
import org.example.itog.Models.Flight;
import org.example.itog.Repositories.AirportRepository;
import org.example.itog.Repositories.FlightRepository; // Замените на реальный репозиторий
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/flight")
public class FlightViewController {

    private final FlightRepository flightService;
    private final AirportRepository airportRepository;

    @Autowired
    public FlightViewController(FlightRepository flightService, AirportRepository airportRepository) {
        this.flightService = flightService;
        this.airportRepository = airportRepository;
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping
    public String getAllFlights(Model model) {
        model.addAttribute("flights", flightService.findAll());

        return "flight/list";  // указывает на flights/list.html
    }
    @PreAuthorize("hasAuthority('ADMIN')")

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("flight", new Flight());
        model.addAttribute("airports", airportRepository.findAll());
        return "flight/form";  // указывает на flights/form.html
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String createFlight(@ModelAttribute Flight flight, RedirectAttributes redirectAttributes) {
        flightService.save(flight);
        redirectAttributes.addFlashAttribute("success", "Рейс успешно добавлен");
        return "redirect:/flight";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return flightService.findById(id).map(flight -> {
            model.addAttribute("flight", flight);
            model.addAttribute("airports", airportRepository.findAll());
            return "flight/form"; // указывает на flights/form.html
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("error", "Рейс не найден");
            return "redirect:/flight";
        });
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update/{id}")
    public String updateFlight(@PathVariable Long id, @ModelAttribute Flight flightDetails, RedirectAttributes redirectAttributes) {
        flightService.findById(id).ifPresent(flight -> {
            flight.setFlightNumber(flightDetails.getFlightNumber());
            flight.setDepartureAirport(flightDetails.getDepartureAirport());
            flight.setArrivalAirport(flightDetails.getArrivalAirport());
            flight.setDepartureTime(flightDetails.getDepartureTime());
            flight.setArrivalTime(flightDetails.getArrivalTime());
            flight.setAvailableSeats(flightDetails.getAvailableSeats());
            flightService.save(flight);
            redirectAttributes.addFlashAttribute("success", "Рейс успешно обновлен");
        });
        return "redirect:/flight";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteFlight(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (flightService.findById(id).isPresent()) {
            flightService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Рейс успешно удален");
        } else {
            redirectAttributes.addFlashAttribute("error", "Рейс не найден");
        }
        return "redirect:/flight";
    }
}
