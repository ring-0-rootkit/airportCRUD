package ru.airport.integration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import ru.airport.dto.FlightCreateEditDto;
import ru.airport.dto.FlightFilter;
import ru.airport.dto.SortingOrderByCost;
import ru.airport.integration.annotation.IT;
import ru.airport.service.FlightService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightServiceTest {
    
    private final FlightService flightService;
    private static final int FLIGHT_1_ID = 1;
    private static final String NEW_FLIGHT_CITY_FROM = "Brazil";
    private static final String NEW_FLIGHT_CITY_TO = "Minsk";
    private static final LocalDate NEW_FLIGHT_DATE_OUT = LocalDate.of(2021, 2, 24);
    private static final int NEW_FLIGHT_AIRCRAFT_ID = 1;
    private static final int NEW_FLIGHT_COST = 1000;

    @Test
    @Order(1)
    void findByIdTest() {
        var mayBeFlight = flightService.findById(FLIGHT_1_ID);
        assertTrue(mayBeFlight.isPresent());
        assertEquals(mayBeFlight.get().getId(), FLIGHT_1_ID);
    }

    @Test
    @Order(2)
    void findAllByFullFilterTest() {
        var filter = new FlightFilter(
                "Paris",
                "London",
                LocalDate.of(2020, 3, 5),
                SortingOrderByCost.ASCENDING
        );
        var result = flightService.findAllByFilter(filter);
        assertThat(result).hasSize(1);
        assertEquals(result.get(0).getCityFrom(), "Paris");
        assertEquals(result.get(0).getCityTo(), "London");
        assertEquals(result.get(0).getCost(), 1000);
    }

    @Test
    @Order(3)
    void findAllByPartFilterTest() {
        var filter = new FlightFilter(
            "Paris",
            null,
            null,
            SortingOrderByCost.ASCENDING
        );
        var result =  flightService.findAllByFilter(filter);
        assertThat(result).hasSize(3);
        assertEquals(result.get(0).getCityFrom(), "Paris");
        assertEquals(result.get(0).getCityTo(), "Amsterdam");
    }

    @Test
    @Order(4)
    void findAllByEmptyFilterTest() {
        var filter = new FlightFilter(
            null,
            null,
            null,
            null
        );
        var result = flightService.findAllByFilter(filter);
        assertThat(result).hasSize(14);
        assertEquals(result.get(0).getId(), 3);
        assertEquals(result.get(0).getCost(), 600);
    }

    @Test
    @Order(5)
    void createTest() {
        var toCreate = new FlightCreateEditDto(
                NEW_FLIGHT_AIRCRAFT_ID,
                NEW_FLIGHT_CITY_FROM,
                NEW_FLIGHT_CITY_TO,
                NEW_FLIGHT_DATE_OUT,
                NEW_FLIGHT_COST
        );
        var created = flightService.create(toCreate);
        assertEquals(toCreate.getCityFrom(), created.getCityFrom());
        assertEquals(toCreate.getCityTo(), created.getCityTo());
        assertEquals(toCreate.getDate(), created.getDate());
        assertEquals(toCreate.getCost(), created.getCost());
        assertEquals(toCreate.getAircraftId(), created.getAircraft().getId());
    }

    @Test
    @Order(6)
    void updateTest() {
        var toUpdate = new FlightCreateEditDto(
                NEW_FLIGHT_AIRCRAFT_ID,
                NEW_FLIGHT_CITY_FROM,
                NEW_FLIGHT_CITY_TO,
                NEW_FLIGHT_DATE_OUT,
                NEW_FLIGHT_COST
        );
        var mayBeUpdated = flightService.update(FLIGHT_1_ID, toUpdate);

        assertTrue(mayBeUpdated.isPresent());
        var updated = mayBeUpdated.get();

        assertEquals(FLIGHT_1_ID, updated.getId());
        assertEquals(toUpdate.getCityFrom(), updated.getCityFrom());
        assertEquals(toUpdate.getCityTo(), updated.getCityTo());
        assertEquals(toUpdate.getDate(), updated.getDate());
        assertEquals(toUpdate.getCost(), updated.getCost());
        assertEquals(toUpdate.getAircraftId(), updated.getAircraft().getId());
    }

    @Test
    @Order(7)
    void deleteTest() {
        boolean isDeleted = flightService.delete(FLIGHT_1_ID);
        assertTrue(isDeleted);
    }
}