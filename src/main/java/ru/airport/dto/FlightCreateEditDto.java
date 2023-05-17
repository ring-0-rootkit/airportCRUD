package ru.airport.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class FlightCreateEditDto {

    Integer aircraftId;
    String cityFrom;
    String cityTo;
    LocalDate date;
    Integer cost;
}
