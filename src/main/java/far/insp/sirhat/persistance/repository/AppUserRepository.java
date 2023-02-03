package far.insp.sirhat.persistance.repository;

import far.insp.sirhat.persistance.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByUsernameAndAccountStatusTrue(String username);
    Optional<AppUser> findByIdAndAccountStatusTrue(String id);
    boolean existsByUsername(String username);

}
