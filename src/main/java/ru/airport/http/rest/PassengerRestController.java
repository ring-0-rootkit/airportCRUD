package ru.airport.http.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.airport.dto.PassengerCreateEditDto;
import ru.airport.dto.PassengerIdDto;
import ru.airport.dto.PassengerReadDto;
import ru.airport.dto.TicketReadDto;
import ru.airport.service.PassengerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/passenger")
public class PassengerRestController {

    private final PassengerService passengerService;

    @PostMapping
    public PassengerReadDto goToUpdate(@RequestBody PassengerIdDto passenger) {
        return passengerService.findById(passenger.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public PassengerReadDto findById(@PathVariable("id") Integer id) {
        return passengerService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/tickets/{id}")
    public List<TicketReadDto> getTicketsByPassengerId(@PathVariable("id") Integer id) {
        return passengerService.getTicketsByPassengerId(id);
    }

    @PostMapping("/{id}")
    public PassengerReadDto update(@PathVariable("id") Integer id,
                         @RequestBody @Validated PassengerCreateEditDto passenger) {
        return passengerService.update(id, passenger)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        if (!passengerService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/registration")
    public PassengerCreateEditDto registration(PassengerCreateEditDto passenger) {
       return passenger;
    }

    @PostMapping("/registration")
    public PassengerReadDto create(@RequestBody @Validated PassengerCreateEditDto passenger) {
        return passengerService.create(passenger);
    }
}
