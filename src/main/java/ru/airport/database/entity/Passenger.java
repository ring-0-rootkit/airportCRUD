package ru.airport.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Data
@ToString(exclude = "tickets")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "passenger")
public class Passenger implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id", nullable = false)
    private Integer id;

    @Column(nullable = false)
    private Integer age;
    private String sex;

    @Builder.Default
    @OneToMany(mappedBy = "passenger")
    private Set<Ticket> tickets = new HashSet<>();

    public void deleteTicket(Ticket ticket) {
        log.info("Ticket No: " + ticket.getId() + " have been deleted at passenger No: " + id);
        tickets.remove(ticket);
    }
}
