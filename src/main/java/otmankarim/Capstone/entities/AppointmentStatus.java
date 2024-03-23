package otmankarim.Capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "appointment_status")
@Getter
@Setter
@NoArgsConstructor
public class AppointmentStatus {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String status;
    @JsonIgnore
    @OneToMany(mappedBy = "appointmentStatus")
    private Set<Appointment> appointments = new LinkedHashSet<>();


    public AppointmentStatus(String status) {
        this.status = status;
    }
}

