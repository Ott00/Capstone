package otmankarim.Capstone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otmankarim.Capstone.entities.AppointmentStatus;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentStatusDAO extends JpaRepository<AppointmentStatus, UUID> {
    boolean existsByStatus(String status);

    Optional<AppointmentStatus> findByStatus(String status);
}
