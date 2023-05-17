package ru.airport.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.airport.database.entity.Flight;

import java.util.List;

public interface FlightRepository extends FilterFlightRepository, JpaRepository<Flight, Integer> {

    @Query("select f from Flight f where f.aircraft.id = :id")
    List<Flight> findAllFlightsByAircraftId(Integer id);
}
