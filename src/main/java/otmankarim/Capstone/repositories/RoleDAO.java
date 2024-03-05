package otmankarim.Capstone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otmankarim.Capstone.entities.Role;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleDAO extends JpaRepository<Role, UUID> {
    Optional<Role> findByRole(String role);
}
