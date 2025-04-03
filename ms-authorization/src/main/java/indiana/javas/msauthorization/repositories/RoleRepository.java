package indiana.javas.msauthorization.repositories;

import indiana.javas.msauthorization.entities.Role;
import indiana.javas.msauthorization.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.role IN :roles")
    List<Role> findAllByRole(@Param("roles") Set<ERole> roles);

    Optional<Role> findByRole(ERole role);
}
