package ru.airport.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.airport.database.entity.Passenger;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

    @Query("select t.passenger.id from Ticket t where t.flight.id = :flightId")
    List<Integer> findAllPassengerIdsByFlightId(Integer flightId);
}
