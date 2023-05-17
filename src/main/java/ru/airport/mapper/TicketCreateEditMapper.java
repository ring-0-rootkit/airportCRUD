package ru.airport.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.airport.database.entity.Flight;
import ru.airport.database.entity.Passenger;
import ru.airport.database.entity.Ticket;
import ru.airport.database.repository.FlightRepository;
import ru.airport.database.repository.PassengerRepository;
import ru.airport.dto.TicketCreateEditDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TicketCreateEditMapper implements Mapper<TicketCreateEditDto, Ticket> {

    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;

    @Override
    public Ticket map(TicketCreateEditDto fromObject) {
        return copy(fromObject, new Ticket());
    }

    @Override
    public Ticket map(TicketCreateEditDto fromObject, Ticket toObject) {
        return copy(fromObject, toObject);
    }

    private Ticket copy(TicketCreateEditDto fromObject, Ticket toObject) {
        toObject.setPassenger(getPassenger(fromObject.getPassengerId()));
        toObject.setFlight(getFlight(fromObject.getFlightId()));
        return toObject;
    }

    private Passenger getPassenger(Integer passengerId) {
        return Optional.ofNullable(passengerId)
                .flatMap(passengerRepository::findById)
                .orElse(null);
    }

    private Flight getFlight(Integer flightId) {
        return Optional.ofNullable(flightId)
                .flatMap(flightRepository::findById)
                .orElse(null);
    }
}
