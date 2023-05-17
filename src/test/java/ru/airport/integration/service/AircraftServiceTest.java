package ru.airport.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import ru.airport.dto.AircraftCreateEditDto;
import ru.airport.integration.annotation.IT;
import ru.airport.service.AircraftService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AircraftServiceTest {

    private final AircraftService aircraftService;

    private static final String NEW_COMPANY = "test";
    private static final String NEW_MODEL = "test";

    @Test
    @Order(1)
    void findAllTest() {
        var aircrafts = aircraftService.findAll();
        assertNotNull(aircrafts);
        assertThat(aircrafts).hasSize(3);
    }

    @Test
    @Order(2)
    void createTest() {
        var newAircraft = new AircraftCreateEditDto(
                NEW_COMPANY,
                NEW_MODEL
        );
        var created = aircraftService.create(newAircraft);
        assertNotNull(created);
        assertEquals(created.getCompany(), newAircraft.getCompany());
        assertEquals(created.getModel(), newAircraft.getModel());
    }

    @Test
    @Order(3)
    void deleteTest() {
        var newAircraft = new AircraftCreateEditDto(
                NEW_COMPANY,
                NEW_MODEL
        );
        var created = aircraftService.create(newAircraft);

        var aircraftsBeforeDelete = aircraftService.findAll();
        assertNotNull(aircraftsBeforeDelete);
        assertThat(aircraftsBeforeDelete).hasSize(4);

        boolean isDeleted = aircraftService.delete(created.getId());
        assertTrue(isDeleted);

        var aircraftsAfterDelete = aircraftService.findAll();
        assertNotNull(aircraftsAfterDelete);
        assertThat(aircraftsAfterDelete).hasSize(3);

    }
}
