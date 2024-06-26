package otmankarim.Capstone.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import otmankarim.Capstone.entities.Category;
import otmankarim.Capstone.entities.Performance;
import otmankarim.Capstone.entities.User;

import java.util.UUID;

@Repository
public interface PerformanceDAO extends JpaRepository<Performance, UUID> {
    @Query("SELECT COUNT(p) > 0 FROM Performance p WHERE p.title = :name AND p.freelancer = :freelancer")
    boolean existsByNameAndFreelancer(String name, User freelancer);

    Page<Performance> findByFreelancer(User freelancer,
                                       Pageable pageable);

    Page<Performance> findByCategory(Pageable pageable, Category category);

    @Query("SELECT p FROM Performance p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :input, '%'))")
    Page<Performance> findByInput(Pageable pageable, String input);
}