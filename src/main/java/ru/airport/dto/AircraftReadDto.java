package ru.airport.dto;

import lombok.Value;

@Value
public class AircraftReadDto {

    Integer id;
    String company;
    String model;

    @Override
    public String toString() {
        return company + " " + model;
    }
}
