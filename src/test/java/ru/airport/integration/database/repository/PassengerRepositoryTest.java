package ru.airport.integration.database.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.airport.database.entity.Passenger;
import ru.airport.database.repository.PassengerRepository;
import ru.airport.integration.annotation.IT;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
@Slf4j
class PassengerRepositoryTest {

    private final PassengerRepository passengerRepository;

    private static final Integer PASS_1_AGE = 10;
    private static final String PASS_1_SEX = "M";
    private static final Integer FLIGHT_1 = 1;

    @Test
    void findByIdTest() {
        Optional<Passenger> mayBePassenger = passengerRepository.findById(1);
        assertTrue(mayBePassenger.isPresent());
        Passenger passenger = mayBePassenger.get();
        assertEquals(PASS_1_AGE, passenger.getAge());
        assertEquals(PASS_1_SEX, passenger.getSex());
        assertThat(passenger.getTickets()).hasSize(4);
    }

    @Test
    void findAllPassengerIdsByFlightIdTest() {
        var passengers = passengerRepository.findAllPassengerIdsByFlightId(FLIGHT_1);
        log.info(passengers.toString());
    }
}