package ru.airport.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.airport.database.repository.AircraftRepository;
import ru.airport.database.repository.FlightRepository;
import ru.airport.dto.AircraftCreateEditDto;
import ru.airport.dto.AircraftReadDto;
import ru.airport.mapper.AircraftCreateEditMapper;
import ru.airport.mapper.AircraftReadMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftReadMapper aircraftReadMapper;
    private final AircraftCreateEditMapper aircraftCreateEditMapper;
    private final FlightRepository flightRepository;
    private final FlightService flightService;

    public List<AircraftReadDto> findAll() {
        return aircraftRepository.findAll().stream()
                .map(aircraftReadMapper::map)
                .toList();
    }

    @Transactional
    public AircraftReadDto create(AircraftCreateEditDto aircraft) {
        return Optional.of(aircraft)
                .map(aircraftCreateEditMapper::map)
                .map(aircraftRepository::save)
                .map(aircraftReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Integer id) {
        return aircraftRepository.findById(id)
                .map(aircraft -> {
                    flightRepository.findAllFlightsByAircraftId(aircraft.getId())
                            .forEach(flight -> flightService.delete(flight.getId()));
                    aircraftRepository.delete(aircraft);
                    aircraftRepository.flush();
                    log.info("Aircraft No:" + aircraft.getId() + " have been deleted!");
                    return true;
                })
                .orElse(false);
    }
}
