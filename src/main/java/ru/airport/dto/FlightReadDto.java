package ru.airport.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class FlightReadDto {

    Integer id;
    AircraftReadDto aircraft;
    String cityFrom;
    String cityTo;
    LocalDate date;
    Integer cost;

    @Override
    public String toString() {
        return "Flight №" + id + "  " +
               "From: " + cityFrom + "  " +
               "To: " + cityTo + "  " +
               "Date: " + date + "  " +
               "Cost: " + cost + "  " +
               "Aircraft: " + aircraft.getCompany() + " " + aircraft.getModel();
    }
}
