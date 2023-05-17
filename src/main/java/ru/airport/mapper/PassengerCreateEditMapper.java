package ru.airport.mapper;

import org.springframework.stereotype.Component;
import ru.airport.database.entity.Passenger;
import ru.airport.dto.PassengerCreateEditDto;

@Component
public class PassengerCreateEditMapper implements Mapper<PassengerCreateEditDto, Passenger> {
    @Override
    public Passenger map(PassengerCreateEditDto fromObject) {
        return copy(fromObject, new Passenger());
    }

    @Override
    public Passenger map(PassengerCreateEditDto fromObject, Passenger toObject) {
        return copy(fromObject, toObject);
    }

    private Passenger copy(PassengerCreateEditDto fromObject, Passenger toObject) {
        toObject.setAge(fromObject.getAge());
        toObject.setSex(fromObject.getSex());
        return toObject;
    }
}
