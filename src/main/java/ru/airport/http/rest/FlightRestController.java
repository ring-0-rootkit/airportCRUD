package ru.airport.http.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.airport.dto.FlightCreateEditDto;
import ru.airport.dto.FlightFilter;
import ru.airport.dto.FlightReadDto;
import ru.airport.dto.PassengerIdDto;
import ru.airport.dto.SortingOrderByCost;
import ru.airport.dto.TicketCreateEditDto;
import ru.airport.dto.TicketReadDto;
import ru.airport.service.AircraftService;
import ru.airport.service.FlightService;
import ru.airport.service.TicketService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/flight")
@Slf4j
public class FlightRestController {

    private final FlightService flightService;
    private final TicketService ticketService;



    @GetMapping
    public List<FlightReadDto> showAll(FlightFilter filter) {
        return flightService.findAllByFilter(filter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FlightReadDto create(@RequestBody FlightCreateEditDto flight) {
        log.info("trying to create flight: ");
        log.info(flight.toString());
        return flightService.create(flight);
    }

    @GetMapping("/sorting-orders")
    public SortingOrderByCost[] getSortingOrders() {
        return SortingOrderByCost.values();
    }

    @GetMapping("/{id}")
    public FlightReadDto orderFlight(@PathVariable("id") Integer id) {
        return flightService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        if (!flightService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/ticket/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable("id") Integer id) {
        if (!ticketService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/ticket")
    public TicketReadDto buyTicket(@RequestParam Integer flightId,
                                   @RequestBody PassengerIdDto passengerIdDto) {
        try {
            log.info("passenger: " + passengerIdDto.getId() + " trying to book flight: " + flightId);
            var flight = flightService.findById(flightId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            return ticketService.create(new TicketCreateEditDto(passengerIdDto.getId(),
                    flight.getId()));
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
