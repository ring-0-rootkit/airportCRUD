package ru.airport.http.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.airport.dto.AircraftCreateEditDto;
import ru.airport.dto.AircraftReadDto;
import ru.airport.service.AircraftService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aircraft")
public class AircraftRestController {

    private final AircraftService aircraftService;

    @GetMapping
    public List<AircraftReadDto> findAll() {
        return aircraftService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        if (!aircraftService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AircraftReadDto create(@RequestBody AircraftCreateEditDto aircraft) {
        return aircraftService.create(aircraft);
    }
}
