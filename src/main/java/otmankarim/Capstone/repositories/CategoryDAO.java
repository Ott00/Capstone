package otmankarim.Capstone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otmankarim.Capstone.entities.Category;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryDAO extends JpaRepository<Category, UUID> {
    Optional<Category> findByName(String name);
}
