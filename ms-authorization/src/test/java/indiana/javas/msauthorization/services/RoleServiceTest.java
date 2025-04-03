package indiana.javas.msauthorization.services;

import indiana.javas.msauthorization.entities.Role;
import indiana.javas.msauthorization.enums.ERole;
import indiana.javas.msauthorization.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {RoleService.class})
public class RoleServiceTest {

    @Autowired
    @InjectMocks
    private RoleService roleService;

    @MockitoBean
    @Mock
    private RoleRepository roleRepository;

    @Test
    public void save_WithValidRole_SavesAndReturnsRole() {
        // Arrange
        Role role = new Role(ERole.ROLE_ADMIN);

        when(roleRepository.save(any(Role.class))).thenReturn(role);

        // Act
        Role savedRole = roleService.save(ERole.ROLE_ADMIN);

        // Assert
        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getRole()).isEqualTo(ERole.ROLE_ADMIN);
    }

    @Test
    public void findAllByRole_WithValidRoles_ReturnsRoleList() {
        // Arrange
        Set<ERole> roles = Set.of(ERole.ROLE_ADMIN, ERole.ROLE_OPERATOR);

        when(roleRepository.findAllByRole(roles)).thenReturn(roles.stream().map(Role::new).toList());

        // Act
        List<Role> result = roleService.findAllByRole(roles);

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void findAllByRole_WithEmptyRoles_ReturnsEmptyList() {
        // Arrange
        Set<ERole> roles = Set.of();

        when(roleRepository.findAllByRole(roles)).thenReturn(List.of());

        // Act
        List<Role> result = roleService.findAllByRole(roles);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    public void findByDescription_WithValidRole_ReturnsOptionalRole() {
        // Arrange
        Role role = new Role(ERole.ROLE_ADMIN);

        when(roleRepository.findByRole(ERole.ROLE_ADMIN)).thenReturn(Optional.of(role));

        // Act
        Optional<Role> result = roleService.findByDescription(ERole.ROLE_ADMIN);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getRole()).isEqualTo(ERole.ROLE_ADMIN);
    }
}