package indiana.javas.msauthorization.repositories;

import indiana.javas.msauthorization.entities.Role;
import indiana.javas.msauthorization.enums.ERole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void createRole_WithValidData_ReturnsRole() {
        // Arrange
        Role adminRole = new Role(ERole.ROLE_ADMIN);

        // Act
        Role savedRole = roleRepository.save(adminRole);

        // Assert
        assertNotNull(savedRole.getId());
        assertEquals(ERole.ROLE_ADMIN, savedRole.getRole());
    }

    @Test
    public void createRole_WithInvalidData_ThrowsException() {
        Role adminRole = new Role();

        assertThatThrownBy(() -> roleRepository.save(adminRole)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void createRole_DuplicateRole_ThrowsException() {
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        Role duplicateAdminRole = new Role(ERole.ROLE_ADMIN);

        roleRepository.save(adminRole);

        assertThatThrownBy(() -> roleRepository.save(duplicateAdminRole)).isInstanceOf(DataIntegrityViolationException.class);
    }


    @Test
    public void findByRole_ExistingRole_ReturnsRole() {
        // Arrange
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        entityManager.persistAndFlush(adminRole);

        // Act
        Optional<Role> foundRole = roleRepository.findByRole(ERole.ROLE_ADMIN);

        // Assert
        assertTrue(foundRole.isPresent());
        assertEquals(ERole.ROLE_ADMIN, foundRole.get().getRole());
    }

    @Test
    public void findRole_NotExistingRole_ReturnsEmpty() {
        // Act
        Optional<Role> foundRole = roleRepository.findByRole(ERole.ROLE_OPERATOR);

        // Assert
        assertFalse(foundRole.isPresent());
    }

    @Test
    public void findRole_AllRoles_ReturnsAllRoles() {
        // Arrange
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        entityManager.persistAndFlush(adminRole);

        Role userRole = new Role(ERole.ROLE_OPERATOR);
        entityManager.persistAndFlush(userRole);

        // Act
        List<Role> roles = roleRepository.findAllByRole(Set.of(ERole.ROLE_ADMIN, ERole.ROLE_OPERATOR));

        // Assert
        assertThat(roles.size()).isEqualTo(2);
        assertTrue(roles.stream().anyMatch(role -> role.getRole() == ERole.ROLE_ADMIN));
        assertTrue(roles.stream().anyMatch(role -> role.getRole() == ERole.ROLE_OPERATOR));
    }

    @Test
    public void findRole_AllSpecifiedRoles_ReturnsAllSpecifiedRoles() {
        // Arrange
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        entityManager.persistAndFlush(adminRole);

        Role userRole = new Role(ERole.ROLE_OPERATOR);
        entityManager.persistAndFlush(userRole);

        // Act
        List<Role> roles = roleRepository.findAllByRole(Set.of(ERole.ROLE_ADMIN));

        // Assert
        assertEquals(1, roles.size());
        assertEquals(ERole.ROLE_ADMIN, roles.get(0).getRole());
    }
}
