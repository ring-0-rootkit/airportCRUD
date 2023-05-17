package ru.airport.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class PassengerCreateEditDto {

    @Max(99)
    @Min(7)
    Integer age;

    @Size(min = 1, max = 1)
    String sex;
}
