package otmankarim.Capstone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import otmankarim.Capstone.entities.Appointment;
import otmankarim.Capstone.entities.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Repository
public interface AppointmentDAO extends JpaRepository<Appointment, UUID> {
    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.client = :client AND a.date = :date AND a.time = :time")
    boolean existsAppointmentInSameDateTime(LocalDate date, LocalTime time, User client);
}