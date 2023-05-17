package ru.airport.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.airport.database.entity.Flight;
import ru.airport.database.entity.Passenger;
import ru.airport.database.entity.Ticket;
import ru.airport.database.repository.PassengerRepository;
import ru.airport.database.repository.TicketRepository;
import ru.airport.dto.TicketCreateEditDto;
import ru.airport.dto.TicketReadDto;
import ru.airport.mapper.TicketCreateEditMapper;
import ru.airport.mapper.TicketReadMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketReadMapper ticketReadMapper;
    private final TicketCreateEditMapper ticketCreateEditMapper;
    private final PassengerRepository passengerRepository;

    public List<TicketReadDto> findAllTicketsByPassengerId(Integer id) {
        return passengerRepository.findById(id)
                .get().getTickets()
                .stream()
                .map(ticketReadMapper::map)
                .toList();
    }

    @Transactional
    public void deleteAllTicketsByPassenger(Passenger passenger) {
        ticketRepository.deleteAllTicketsByPassengerId(passenger.getId());
        ticketRepository.flush();
    }

    @Transactional
    public void deleteAllTicketsByFlight(Flight flight) {
        ticketRepository.findAllTicketsByFlightId(flight.getId())
                .forEach(ticket -> ((Passenger)ticket.get("passenger"))
                        .deleteTicket((Ticket)ticket.get("ticket")));
        ticketRepository.deleteAllTicketsByFlightId(flight.getId());
        ticketRepository.flush();
    }

    @Transactional
    public TicketReadDto create(TicketCreateEditDto ticketDto) {
        return Optional.of(ticketDto)
                .map(ticketCreateEditMapper::map)
                .map(ticketRepository::save)
                .map(ticket -> {
                    log.info("Ticket No: " + ticket.getId() + " have been created!");
                    return ticket;
                })
                .map(ticketReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Integer id) {
        return ticketRepository.findById(id)
                .map(ticket -> {
                    ticket.getPassenger().deleteTicket(ticket);
                    ticketRepository.delete(ticket);
                    ticketRepository.flush();
                    log.info("Ticket No: " + id + " have been deleted!");
                    return true;
                })
                .orElse(false);
    }
}
