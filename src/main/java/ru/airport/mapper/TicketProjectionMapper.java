package ru.airport.mapper;

import org.springframework.stereotype.Component;
import ru.airport.database.repository.TicketRepository;
import ru.airport.dto.TicketReadDto;

@Component
public class TicketProjectionMapper {

    public TicketReadDto map(TicketRepository.FlightProjection projection) {
        return new TicketReadDto(
            projection.getTicketId(),
            projection.getFlightId(),
            projection.getPassengerId(),
            projection.getCityFrom(),
            projection.getCityTo(),
            projection.getDateOut(),
            projection.getAircraft(),
            projection.getCost()
        );
    }
}
