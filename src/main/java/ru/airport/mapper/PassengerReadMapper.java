package ru.airport.mapper;

import org.springframework.stereotype.Component;
import ru.airport.database.entity.Passenger;
import ru.airport.dto.PassengerReadDto;

@Component
public class PassengerReadMapper implements Mapper<Passenger, PassengerReadDto> {
    @Override
    public PassengerReadDto map(Passenger object) {
        return new PassengerReadDto(
                object.getId(),
                object.getAge(),
                object.getSex()
        );
    }
}
