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

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @ManyToOne
    @JoinColumn(name = "appointment_status_id")
    private AppointmentStatus appointmentStatus;

    public Appointment(LocalDate date, LocalTime time, AppointmentStatus appointmentStatus, User client, Performance performance) {
        this.date = date;
        this.time = time;
        this.appointmentStatus = appointmentStatus;
        this.client = client;
        this.performance = performance;
    }
}
