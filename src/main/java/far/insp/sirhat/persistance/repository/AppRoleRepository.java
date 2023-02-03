package far.insp.sirhat.persistance.repository;

import far.insp.sirhat.persistance.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    Optional<AppRole> findByRole(String name);
}
