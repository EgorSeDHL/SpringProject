package org.example.itog.Controllers;

import org.example.itog.Models.Luggage;
import org.example.itog.Repositories.LuggageRepository; // Замените на реальный репозиторий
import org.example.itog.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/luggage")
public class LuggageViewController {

    private final LuggageRepository luggageService;
    private final TicketRepository ticketRepository;

    @Autowired
    public LuggageViewController(LuggageRepository luggageService, TicketRepository ticketRepository) {
        this.luggageService = luggageService;
        this.ticketRepository = ticketRepository;
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping
    public String getAllLuggage(Model model) {
        model.addAttribute("luggages", luggageService.findAll());
        return "luggage/list";  // указывает на luggage/list.html
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("luggage", new Luggage());
        model.addAttribute("tickets", ticketRepository.findAll());
        return "luggage/form";  // указывает на luggage/form.html
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping
    public String createLuggage(@ModelAttribute Luggage luggage, RedirectAttributes redirectAttributes) {
        luggageService.save(luggage);
        redirectAttributes.addFlashAttribute("success", "Багаж успешно добавлен");
        return "redirect:/luggage";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return luggageService.findById(id).map(luggage -> {
            model.addAttribute("tickets", ticketRepository.findAll());
            model.addAttribute("luggage", luggage);
            return "luggage/form"; // указывает на luggage/form.html
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("error", "Багаж не найден");
            return "redirect:/luggage";
        });
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update/{id}")
    public String updateLuggage(@PathVariable Long id, @ModelAttribute Luggage luggageDetails, RedirectAttributes redirectAttributes) {
        luggageService.findById(id).ifPresent(luggage -> {
            luggage.setWeight(luggageDetails.getWeight());
            luggage.setDescription(luggageDetails.getDescription());
            luggage.setTicket(luggageDetails.getTicket());
            luggageService.save(luggage);
            redirectAttributes.addFlashAttribute("success", "Багаж успешно обновлен");
        });
        return "redirect:/luggage";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteLuggage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (luggageService.findById(id).isPresent()) {
            luggageService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Багаж успешно удален");
        } else {
            redirectAttributes.addFlashAttribute("error", "Багаж не найден");
        }
        return "redirect:/luggage";
    }
}
