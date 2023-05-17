package ru.airport.integration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import ru.airport.dto.PassengerCreateEditDto;
import ru.airport.integration.annotation.IT;
import ru.airport.service.PassengerService;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@IT
@Slf4j
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PassengerServiceTest {

    private final PassengerService passengerService;

    private static final int USER_1_ID = 1;
    private static final int PASSENGER_1_ID = 1;
    private static final int USER_1_AGE = 10;
    private static final int NEW_PASSENGER_AGE = 30;
    private static final String NEW_PASSENGER_SEX = "M";


    @Test
    @Order(1)
    void findByIdTest() {
        var mayBePassenger = passengerService.findById(USER_1_ID);
        assertTrue(mayBePassenger.isPresent());
        var passenger = mayBePassenger.get();
        assertEquals(passenger.getId(), USER_1_ID);
        assertEquals(passenger.getAge(), USER_1_AGE);
    }

    @Test
    @Order(2)
    void findAllPassengerIdsByFlightIdTest() {
        var passengers = passengerService.findAllPassengerIdsByFlightId(2);
        assertThat(passengers).hasSize(2);
        log.info(passengers.toString());
    }

    @Test
    @Order(4)
    void createTest() {
        var newPass = new PassengerCreateEditDto(
                NEW_PASSENGER_AGE,
                NEW_PASSENGER_SEX
        );
        var createdPass = passengerService.create(newPass);
        assertEquals(newPass.getAge(), createdPass.getAge());
        assertEquals(newPass.getSex(), createdPass.getSex());
    }

    @Test
    @Order(5)
    void updateTest() {
        var newPass = new PassengerCreateEditDto(
                NEW_PASSENGER_AGE,
                NEW_PASSENGER_SEX
        );
        var mayBePreviousPassenger = passengerService.findById(USER_1_ID);
        var mayBeNewPassenger = passengerService.update(USER_1_ID, newPass);

        assertTrue(mayBeNewPassenger.isPresent());
        assertTrue(mayBePreviousPassenger.isPresent());

        var previousPassenger = mayBePreviousPassenger.get();
        var newPassenger = mayBeNewPassenger.get();

        assertNotEquals(previousPassenger, newPassenger);
        assertEquals(newPass.getAge(), newPassenger.getAge());
        assertEquals(newPass.getSex(), newPassenger.getSex());
    }

    @Test
    @Order(6)
    void deleteTest() {
        var beforeDelete = passengerService.findById(USER_1_ID);
        assertTrue(beforeDelete.isPresent());
        boolean isDeleted = passengerService.delete(USER_1_ID);
        assertTrue(isDeleted);
    }

    @Test
    @Order(3)
    void getTicketsByPassengerIdTest() {
        var tickets = passengerService.getTicketsByPassengerId(PASSENGER_1_ID);
        assertNotNull(tickets);
        assertThat(tickets).hasSize(4);
        for (var ticket : tickets) {
            assertEquals(ticket.getPassengerId(), 1);
        }
    }
}