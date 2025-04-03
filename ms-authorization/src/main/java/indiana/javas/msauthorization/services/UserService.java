package indiana.javas.msauthorization.services;

import indiana.javas.msauthorization.entities.User;
import indiana.javas.msauthorization.enums.ERole;
import indiana.javas.msauthorization.exceptions.UserNotFoundException;
import indiana.javas.msauthorization.producers.UserProducer;
import indiana.javas.msauthorization.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserProducer userProducer;

    @Transactional
    public User save(User user, Set<ERole> roles) {
        if (roles == null || roles.isEmpty()) {
            roles = new HashSet<>();
            roles.add(ERole.ROLE_OPERATOR);
        }

        var userRoles = new HashSet<>(roleService.findAllByRole(roles));
        user.setRoles(userRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("Could not find user with ID '%d'", id))
        );
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void update(Long id, User newUser, Set<ERole> roles) {
        var user = findById(id);

        if (newUser.getFirstName() != null) {
            user.setFirstName(newUser.getFirstName());
        }

        if (newUser.getLastName() != null) {
            user.setLastName(newUser.getLastName());
        }

        if (newUser.getEmail() != null) {
            user.setEmail(newUser.getEmail());
        }

        if (newUser.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }

        if (roles != null) {
            var userRoles = new HashSet<>(roleService.findAllByRole(roles));
            user.setRoles(userRoles);
        }

        userRepository.save(user);
        userProducer.publishMessage(user);
    }
}
