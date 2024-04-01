package otmankarim.Capstone.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import otmankarim.Capstone.entities.Performance;
import otmankarim.Capstone.entities.Review;
import otmankarim.Capstone.entities.User;

import java.util.UUID;

@Repository
public interface ReviewDAO extends JpaRepository<Review, UUID> {
    @Query("SELECT COUNT(r) > 0 FROM Review r WHERE r.client = :client AND r.performance = :performance")
    boolean existsReviewForSamePerformance(Performance performance, User client);

    Review findReviewByPerformanceAndClient(Performance performance, User client);

    @Query("SELECT r FROM Review r JOIN r.performance p WHERE p.freelancer = :freelancer")
    Page<Review> findByFreelancer(User freelancer, Pageable pageable);

    Page<Review> findByClient(User client, Pageable pageable);
}
