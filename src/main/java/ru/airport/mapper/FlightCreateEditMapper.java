package ru.airport.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.airport.database.entity.Aircraft;
import ru.airport.database.entity.Flight;
import ru.airport.database.repository.AircraftRepository;
import ru.airport.dto.FlightCreateEditDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FlightCreateEditMapper implements Mapper<FlightCreateEditDto, Flight> {

    private final AircraftRepository aircraftRepository;

    @Override
    public Flight map(FlightCreateEditDto fromObject) {
        return copy(fromObject, new Flight());
    }

    @Override
    public Flight map(FlightCreateEditDto fromObject, Flight toObject) {
        return copy(fromObject, toObject);
    }

    private Flight copy(FlightCreateEditDto fromObject, Flight toObject) {
        toObject.setCityFrom(fromObject.getCityFrom());
        toObject.setCityTo(fromObject.getCityTo());
        toObject.setDateOut(fromObject.getDate());
        toObject.setCost(fromObject.getCost());
        toObject.setAircraft(getAircraft(fromObject.getAircraftId()));
        return toObject;
    }

    private Aircraft getAircraft(Integer aircraftId) {
        return Optional.ofNullable(aircraftId)
                .flatMap(aircraftRepository::findById)
                .orElse(null);
    }
}
