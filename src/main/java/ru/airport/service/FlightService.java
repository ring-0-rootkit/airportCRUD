package ru.airport.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.airport.database.repository.FlightRepository;
import ru.airport.dto.FlightCreateEditDto;
import ru.airport.dto.FlightFilter;
import ru.airport.dto.FlightReadDto;
import ru.airport.mapper.FlightCreateEditMapper;
import ru.airport.mapper.FlightReadMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightReadMapper flightReadMapper;
    private final FlightCreateEditMapper flightCreateEditMapper;
    private final TicketService ticketService;

    public Optional<FlightReadDto> findById(Integer id) {
        return flightRepository.findById(id)
                .map(flightReadMapper::map);
    }

    public List<FlightReadDto> findAll() {
        return flightRepository.findAll().stream()
                .map(flightReadMapper::map)
                .toList();
    }

    public List<FlightReadDto> findAllByFilter(FlightFilter filter) {
        return flightRepository.findAllByFilter(filter).stream()
                .map(flightReadMapper::map)
                .toList();
    }

    @Transactional
    public FlightReadDto create(FlightCreateEditDto flightDto) {
        return Optional.of(flightDto)
                .map(flightCreateEditMapper::map)
                .map(flightRepository::save)
                .map(flight -> {
                    log.info("Flight No: " + flight.getId() + " have been created!");
                    return flight;
                })
                .map(flightReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<FlightReadDto> update(Integer id, FlightCreateEditDto flightDto) {
        return flightRepository.findById(id)
                .map(flight -> flightCreateEditMapper.map(flightDto, flight))
                .map(flightRepository::saveAndFlush)
                .map(flight -> {
                    log.info("Flight No: " + flight.getId() + " have been updated!");
                    return flight;
                })
                .map(flightReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return flightRepository.findById(id)
                .map(flight -> {
                    ticketService.deleteAllTicketsByFlight(flight);
                    flightRepository.delete(flight);
                    flightRepository.flush();
                    log.info("Flight No: " + id + " have been deleted!");
                    return true;
                })
                .orElse(false);
    }
}
