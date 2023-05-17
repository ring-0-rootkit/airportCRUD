package ru.airport.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.airport.database.entity.Flight;
import ru.airport.dto.AircraftReadDto;
import ru.airport.dto.FlightReadDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FlightReadMapper implements Mapper<Flight, FlightReadDto> {

    private final AircraftReadMapper aircraftReadMapper;
    @Override
    public FlightReadDto map(Flight object) {
        AircraftReadDto aircraft = Optional.ofNullable(object.getAircraft())
                .map(aircraftReadMapper::map)
                .orElse(null);
        return new FlightReadDto(
                object.getId(),
                aircraft,
                object.getCityFrom(),
                object.getCityTo(),
                object.getDateOut(),
                object.getCost()
        );
    }
}
