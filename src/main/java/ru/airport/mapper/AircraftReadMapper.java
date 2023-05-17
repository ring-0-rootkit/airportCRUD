package ru.airport.mapper;

import org.springframework.stereotype.Component;
import ru.airport.database.entity.Aircraft;
import ru.airport.dto.AircraftReadDto;

@Component
public class AircraftReadMapper implements Mapper<Aircraft, AircraftReadDto> {
    @Override
    public AircraftReadDto map(Aircraft object) {
        return new AircraftReadDto(
                object.getId(),
                object.getCompany(),
                object.getModel()
        );
    }
}
