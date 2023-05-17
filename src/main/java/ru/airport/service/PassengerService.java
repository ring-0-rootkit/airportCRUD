package ru.airport.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.airport.database.repository.PassengerRepository;
import ru.airport.dto.PassengerCreateEditDto;
import ru.airport.dto.PassengerReadDto;
import ru.airport.dto.TicketReadDto;
import ru.airport.mapper.PassengerCreateEditMapper;
import ru.airport.mapper.PassengerReadMapper;
import ru.airport.mapper.TicketReadMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerReadMapper passengerReadMapper;
    private final PassengerCreateEditMapper passengerCreateEditMapper;
    private final TicketService ticketService;
    private final TicketReadMapper ticketReadMapper;

    public Optional<PassengerReadDto> findById(Integer id) {
        return passengerRepository.findById(id)
                .map(passengerReadMapper::map);
    }

    public List<TicketReadDto> getTicketsByPassengerId(Integer id) {
        return passengerRepository.findById(id)
                .get().getTickets().stream()
                .map(ticketReadMapper::map)
                .toList();
    }

    public List<Integer> findAllPassengerIdsByFlightId(Integer id) {
        return passengerRepository.findAllPassengerIdsByFlightId(id);
    }

    @Transactional
    public PassengerReadDto create(PassengerCreateEditDto passengerDto) {
        return Optional.of(passengerDto)
                .map(passengerCreateEditMapper::map)
                .map(passengerRepository::save)
                .map(passenger -> {
                    log.info("Passenger No: " + passenger.getId() + " have been created!");
                    return passenger;
                })
                .map(passengerReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<PassengerReadDto> update(Integer id, PassengerCreateEditDto passengerDto) {
        return passengerRepository.findById(id)
                .map(passenger -> passengerCreateEditMapper.map(passengerDto, passenger))
                .map(passengerRepository::saveAndFlush)
                .map(passenger -> {
                    log.info("Passenger No: " + passenger.getId() + " have been updated!");
                    return passenger;
                })
                .map(passengerReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return passengerRepository.findById(id)
                .map(passenger -> {
                    ticketService.deleteAllTicketsByPassenger(passenger);
                    passengerRepository.delete(passenger);
                    passengerRepository.flush();
                    log.info("Passenger No: " + id + " have been deleted!");
                    return true;
                })
                .orElse(false);
    }
}
