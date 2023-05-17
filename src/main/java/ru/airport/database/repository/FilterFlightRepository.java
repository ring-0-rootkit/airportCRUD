package ru.airport.database.repository;

import ru.airport.database.entity.Flight;
import ru.airport.dto.FlightFilter;

import java.util.List;

public interface FilterFlightRepository {

    List<Flight> findAllByFilter(FlightFilter filter);
}
