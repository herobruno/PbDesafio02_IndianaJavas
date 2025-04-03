package indiana.javas.msauthorization.config;

import indiana.javas.msauthorization.entities.User;
import indiana.javas.msauthorization.enums.ERole;
import indiana.javas.msauthorization.services.RoleService;
import indiana.javas.msauthorization.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;


@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;

    @Override
    public void run(String... args) {
        // Initializes the default roles on the database
        for (var role : ERole.values()) {
            if (roleService.findByDescription(role).isEmpty()) {
                roleService.save(role);
            }
        }


        // Initialize an ADMIN user
        var email = "admin@mail.com";
        if (userService.findByEmail(email).isEmpty()) {
            userService.save(
                    User.builder()
                            .email(email)
                            .firstName("ADMIN")
                            .lastName("ADMIN")
                            .password("admin123")
                            .build(),
                    Set.of(ERole.values())
            );
        }
    }
}
