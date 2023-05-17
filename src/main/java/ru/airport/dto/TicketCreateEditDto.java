package ru.airport.dto;

import lombok.Value;

@Value
public class TicketCreateEditDto {

    Integer passengerId;
    Integer flightId;
}
