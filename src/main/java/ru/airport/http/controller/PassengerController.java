package ru.airport.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.airport.dto.PassengerCreateEditDto;
import ru.airport.dto.PassengerIdDto;
import ru.airport.service.PassengerService;

@Controller
@RequestMapping("/passenger")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping
    public String choseAction() {
        return "passenger/action";
    }

    @PostMapping
    public String goToUpdate(@ModelAttribute PassengerIdDto passenger) {
        passengerService.findById(passenger.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return "redirect:/passenger/" + passenger.getId();
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return passengerService.findById(id)
                .map(passenger -> {
                    model.addAttribute("passenger", passenger);
                    model.addAttribute("tickets",
                            passengerService.getTicketsByPassengerId(passenger.getId()));
                    return "passenger/passenger";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute @Validated PassengerCreateEditDto passenger,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/passenger/" + id;
        }
        return passengerService.update(id, passenger)
                .map(resp -> "redirect:/passenger/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!passengerService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/passenger";
    }

    @GetMapping("/registration")
    public String registration(Model model, PassengerCreateEditDto passenger) {
        model.addAttribute("passenger", passenger);
        return "passenger/registration";
    }

    @PostMapping("/registration")
    public String create(@ModelAttribute @Validated PassengerCreateEditDto passenger,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/passenger/registration";
        }

        return "redirect:/passenger/" + passengerService.create(passenger).getId();
    }
}
