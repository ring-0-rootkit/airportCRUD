package ru.airport.dto;

import java.time.LocalDate;

public record FlightFilter(String cityFrom,
                           String cityTo,
                           LocalDate dateOut,
                           SortingOrderByCost order) {
}
