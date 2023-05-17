package ru.airport.database.repository;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.airport.database.entity.Ticket;

import java.time.LocalDate;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("select t.passenger as passenger, t as ticket from Ticket t where t.flight.id = :flightId")
    List<Tuple> findAllTicketsByFlightId(Integer flightId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Ticket t where t.flight.id = :flightId")
    void deleteAllTicketsByFlightId(Integer flightId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Ticket t where t.passenger.id = :passengerId")
    void deleteAllTicketsByPassengerId(Integer passengerId);

    @Query(value = """
            select   tick.order_id as ticketId,
                     f.flight_id as flightId,
                     :passengerId as passengerId,
                     f.city_from as cityFrom,
                     f.city_to as cityTo,
                     f.date_out as dateOut,
                     concat(air.company, ' ', air.model) as aircraft,
                     f.cost as cost
             from Flight as f
             join (
             select t.flight_id,
                    t.order_id
             from Ticket as t
             where t.passenger_id = :passengerId
             ) tick
             using (flight_id)
             join Aircraft air
             using (aircraft_id)
            """,  nativeQuery = true)
    List<FlightProjection> findAllFlightsByPassengerId(Integer passengerId);

    interface FlightProjection {
        Integer getTicketId();
        Integer getFlightId();
        Integer getPassengerId();
        String getCityFrom();
        String getCityTo();
        LocalDate getDateOut();
        String getAircraft();
        Integer getCost();
    }
}
