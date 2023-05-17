package ru.airport.mapper;

import org.springframework.stereotype.Component;
import ru.airport.database.entity.Aircraft;
import ru.airport.dto.AircraftCreateEditDto;

@Component
public class AircraftCreateEditMapper implements Mapper<AircraftCreateEditDto, Aircraft> {

    @Override
    public Aircraft map(AircraftCreateEditDto fromObject) {
        return copy(fromObject, new Aircraft());
    }

    @Override
    public Aircraft map(AircraftCreateEditDto fromObject, Aircraft toObject) {
        return copy(fromObject, toObject);
    }

    private Aircraft copy(AircraftCreateEditDto from, Aircraft to) {
        to.setCompany(from.getCompany());
        to.setModel(from.getModel());
        return to;
    }
}
