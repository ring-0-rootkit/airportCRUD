package ru.airport.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import ru.airport.dto.AircraftCreateEditDto;
import ru.airport.service.AircraftService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("aircrafts", aircraftService.findAll());
        return "aircraft/aircraft";
    }

    @GetMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        if (!aircraftService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/aircraft";
    }

    @GetMapping("main")
    public String showMainPage() {
        return "aircraft/main";
    }

    @GetMapping("/create")
    public String showCreatePage() {
        return "aircraft/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute AircraftCreateEditDto aircraft) {
        aircraftService.create(aircraft);
        return "redirect:/aircraft";
    }
}
