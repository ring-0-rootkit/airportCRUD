package ru.airport.mapper;

import org.springframework.stereotype.Component;
import ru.airport.database.entity.Ticket;
import ru.airport.dto.TicketReadDto;

@Component
public class TicketReadMapper implements Mapper<Ticket, TicketReadDto> {


    @Override
    public TicketReadDto map(Ticket object) {
        return new TicketReadDto(
                object.getId(),
                object.getFlight().getId(),
                object.getPassenger().getId(),
                object.getFlight().getCityFrom(),
                object.getFlight().getCityTo(),
                object.getFlight().getDateOut(),
                object.getFlight().getAircraft().getCompany() + " " +
                            object.getFlight().getAircraft().getModel(),
                object.getFlight().getCost()
        );
    }
}
