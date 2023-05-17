package ru.airport.integration.database.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.airport.database.repository.FlightRepository;
import ru.airport.dto.FlightFilter;
import ru.airport.dto.SortingOrderByCost;
import ru.airport.integration.annotation.IT;

import java.time.LocalDate;

@IT
@RequiredArgsConstructor
@Slf4j
class FlightRepositoryTest {

    private static final String CITY_FROM_FILTER = "Paris";
    private static final String CITY_TO_FILTER = "London";
    private static final LocalDate DATE_FILTER = null;

    private final FlightRepository flightRepository;

    @Test
    void findByFilterTest() {
        FlightFilter filter = new FlightFilter(CITY_FROM_FILTER, CITY_TO_FILTER, DATE_FILTER, SortingOrderByCost.ASCENDING);
        var flights = flightRepository.findAllByFilter(filter);
        log.info(flights.toString());
    }
}