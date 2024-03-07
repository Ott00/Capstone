package otmankarim.Capstone.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "id", nullable = false)
    private UUID id;
    private LocalDate date;
    private LocalTime time;
    private boolean confirmation;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;

    public Appointment(LocalDate date, LocalTime time, User client, Performance performance) {
        this.date = date;
        this.time = time;
        this.confirmation = false;
        this.client = client;
        this.performance = performance;
    }
}
