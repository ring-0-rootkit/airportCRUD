package ru.airport.integration.database.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import ru.airport.database.repository.TicketRepository;
import ru.airport.integration.annotation.IT;
import ru.airport.mapper.TicketProjectionMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

@IT
@Slf4j
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TicketRepositoryTest {

    private final TicketRepository ticketRepository;
    private final TicketProjectionMapper ticketProjectionMapper;
    private static final Integer PASSENGER_1 = 1;
    private static final Integer FLIGHT_1 = 1;

    @Test
    @Order(1)
    void findAllFlightsByPassengerIdTest() {
        var flights = ticketRepository.findAllFlightsByPassengerId(PASSENGER_1).stream()
                .map(ticketProjectionMapper::map)
                .toList();
        log.info(flights.toString());
    }

    @Test
    @Order(2)
    void findAllTicketsByFlightIdTest() {
        var tickets = ticketRepository.findAllTicketsByFlightId(FLIGHT_1);
        assertNotNull(tickets);
        assertThat(tickets).hasSize(2);
    }

    @Test
    @Order(3)
    void deleteAllTicketsByFlightId() {
        ticketRepository.deleteAllTicketsByFlightId(FLIGHT_1);
        var ticketsAfter = ticketRepository.findAllTicketsByFlightId(FLIGHT_1);
        assertNotNull(ticketsAfter);
        assertThat(ticketsAfter).hasSize(0);
    }

    @Test
    @Order(4)
    void deleteAllTicketsByPassengerId() {
        ticketRepository.deleteAllTicketsByPassengerId(PASSENGER_1);
        var ticketsAfter = ticketRepository.findAllFlightsByPassengerId(PASSENGER_1);
        assertNotNull(ticketsAfter);
        assertThat(ticketsAfter).hasSize(0);
    }
}
