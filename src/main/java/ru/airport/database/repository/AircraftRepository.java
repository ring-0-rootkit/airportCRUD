package ru.airport.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.airport.database.entity.Aircraft;

public interface AircraftRepository extends JpaRepository<Aircraft, Integer> {
}
