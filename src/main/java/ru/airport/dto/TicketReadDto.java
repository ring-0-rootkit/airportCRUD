package ru.airport.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class TicketReadDto {

    Integer id;
    Integer flightId;
    Integer passengerId;
    String cityFrom;
    String cityTo;
    LocalDate dateOut;
    String aircraft;
    Integer cost;

    @Override
    public String toString() {
        return "Ticket №" + id + "  " +
               "From: " + cityFrom + "  " +
               "To: " + cityTo + "  " +
               "Date: " + dateOut + "  " +
               "Cost: " + cost + "  " +
               "Aircraft: " + aircraft;
    }
}
