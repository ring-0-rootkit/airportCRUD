package ru.airport.http.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import ru.airport.dto.FlightCreateEditDto;
import ru.airport.dto.FlightFilter;
import ru.airport.dto.PassengerIdDto;
import ru.airport.dto.SortingOrderByCost;
import ru.airport.dto.TicketCreateEditDto;
import ru.airport.service.AircraftService;
import ru.airport.service.FlightService;
import ru.airport.service.TicketService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/flight")
@Slf4j
public class FlightController {

    private final FlightService flightService;
    private final AircraftService aircraftService;
    private final TicketService ticketService;

    @PostMapping
    public String buyTicket(@RequestParam Integer flightId,
                            PassengerIdDto passengerIdDto) {
        try {
            log.info("passenger: " + passengerIdDto.getId() + " trying to book flight: " + flightId);
            ticketService.create(new TicketCreateEditDto(passengerIdDto.getId(),
                    flightId));
        } catch(Exception e) {
            return "flight/purchase_error";
        }
        return "redirect:/passenger/" + passengerIdDto.getId();
    }

    @GetMapping
    public String showAll(Model model, FlightFilter filter) {
        model.addAttribute("flights", flightService.findAllByFilter(filter));
        model.addAttribute("ordering", SortingOrderByCost.values());
        return "flight/flights";
    }

    @GetMapping("/{id}")
    public String orderFlight(@PathVariable("id") Integer id,
                              Model model) {
        try {
            model.addAttribute("flight", flightService.findById(id).get());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "flight/flight";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (flightService.delete(id)) {
            return "redirect:/";
        }
        return "flight/no_such_flight";
    }

    @GetMapping("/ticket/{id}/delete")
    public String deleteTicket(@PathVariable("id") Integer id,
                               @RequestParam Integer passengerId) {
        ticketService.delete(id);
        return "redirect:/passenger/" + passengerId;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("aircrafts", aircraftService.findAll());
        return "flight/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute FlightCreateEditDto flight) {
        log.info("trying to create flight: ");
        log.info(flight.toString());
        return "redirect:/flight/" + flightService.create(flight).getId();
    }
}
