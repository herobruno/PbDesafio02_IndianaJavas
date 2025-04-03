package indiana.javas.msauthorization.services;

import indiana.javas.msauthorization.entities.Role;
import indiana.javas.msauthorization.enums.ERole;
import indiana.javas.msauthorization.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public Role save(ERole role) {
        return roleRepository.save(new Role(role));
    }

    @Transactional(readOnly = true)
    public List<Role> findAllByRole(Set<ERole> roles) {
        return roleRepository.findAllByRole(roles);
    }

    @Transactional(readOnly = true)
    public Optional<Role> findByDescription(ERole description) {
        return roleRepository.findByRole(description);
    }
}
