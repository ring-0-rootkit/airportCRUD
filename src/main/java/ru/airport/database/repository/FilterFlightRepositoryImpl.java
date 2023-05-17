package ru.airport.database.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import ru.airport.database.entity.Flight;
import ru.airport.dto.FlightFilter;
import ru.airport.dto.SortingOrderByCost;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterFlightRepositoryImpl implements FilterFlightRepository {

    private final EntityManager entityManager;

    @Override
    public List<Flight> findAllByFilter(FlightFilter filter) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Flight.class);
        var flight = criteria.from(Flight.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.cityFrom() != null && !filter.cityFrom().isBlank()) {
            predicates.add(cb.like(flight.get("cityFrom"), filter.cityFrom()));
        }
        if (filter.cityTo() != null && !filter.cityTo().isBlank()) {
            predicates.add(cb.like(flight.get("cityTo"), filter.cityTo()));
        }
        if (filter.dateOut() != null) {
            predicates.add(cb.equal(flight.get("dateOut"), filter.dateOut()));
        }

        if  (!predicates.isEmpty()) {
            criteria.where(predicates.toArray(Predicate[]::new));
        }

        if (filter.order() == null || filter.order() == SortingOrderByCost.ASCENDING) {
            criteria.orderBy(cb.asc(flight.get("cost")));
        }
        else if (filter.order() == SortingOrderByCost.DESCENDING) {
            criteria.orderBy(cb.desc(flight.get("cost")));
        }

        return entityManager.createQuery(criteria).getResultList();
    }
}
