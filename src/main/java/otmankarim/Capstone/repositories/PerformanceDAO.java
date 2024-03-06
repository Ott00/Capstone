package otmankarim.Capstone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import otmankarim.Capstone.entities.Performance;
import otmankarim.Capstone.entities.User;

import java.util.UUID;

@Repository
public interface PerformanceDAO extends JpaRepository<Performance, UUID> {
    @Query("SELECT COUNT(p) > 0 FROM Performance p WHERE p.title = :name AND p.freelancer = :freelancer")
    boolean existsByNameAndFreelancer(String name, User freelancer);
}