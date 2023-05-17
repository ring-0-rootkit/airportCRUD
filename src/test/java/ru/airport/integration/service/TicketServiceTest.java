package ru.airport.integration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import ru.airport.database.repository.FlightRepository;
import ru.airport.database.repository.PassengerRepository;
import ru.airport.dto.TicketCreateEditDto;
import ru.airport.integration.annotation.IT;
import ru.airport.service.TicketService;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketServiceTest {

    private final TicketService ticketService;
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;
    private static final int PASSENGER_FOR_NEW_TICKET = 9;
    private static final int FLIGHT_FOR_NEW_TICKET = 10;
    private static final int PASSENGER_1_ID = 1;
    private static final int FLIGHT_1_ID = 1;


    @Test
    @Order(1)
    void findAllTicketsByPassengerId() {
        var tickets = ticketService.findAllTicketsByPassengerId(PASSENGER_1_ID);
        assertThat(tickets).hasSize(4);
    }

    @Test
    @Order(2)
    void create() {
        var newTicket = new TicketCreateEditDto(
                PASSENGER_FOR_NEW_TICKET,
                FLIGHT_FOR_NEW_TICKET
        );
        int prevPassengerTicketsQuant = passengerRepository
                .findById(PASSENGER_FOR_NEW_TICKET).get()
                .getTickets()
                .size();

        var createdTicket = ticketService.create(newTicket);

        assertEquals(createdTicket.getFlightId(), FLIGHT_FOR_NEW_TICKET);
        assertEquals(createdTicket.getPassengerId(), PASSENGER_FOR_NEW_TICKET);

        int curPassengerTicketsQuant = passengerRepository
                .findById(PASSENGER_FOR_NEW_TICKET).get()
                .getTickets()
                .size();

        assertNotEquals(prevPassengerTicketsQuant, curPassengerTicketsQuant, "tickets quant are equals");

        assertEquals(prevPassengerTicketsQuant + 1, curPassengerTicketsQuant,
                "tickets quant differs not on 1 ticket");
    }

    @Test
    @Order(3)
    void delete() {
        boolean isDeleted = ticketService.delete(1);
        assertTrue(isDeleted);
    }

    @Test
    @Order(4)
    void deleteAllTicketsByPassengerTest() {
        try {
            var toDeletePreparation = passengerRepository.findById(PASSENGER_1_ID);
            assertTrue(toDeletePreparation.isPresent());
            var toDelete = toDeletePreparation.get();
            ticketService.deleteAllTicketsByPassenger(toDelete);
        } catch(Exception e) {
            fail();
        }
    }

    @Test
    @Order(5)
    void deleteAllTicketsByFlight() {
        try {
            var toDeletePrepare = flightRepository.findById(FLIGHT_1_ID);
            assertTrue(toDeletePrepare.isPresent());
            var toDelete = toDeletePrepare.get();
            ticketService.deleteAllTicketsByFlight(toDelete);
        } catch (Exception e) {
            fail();
        }
    }
}