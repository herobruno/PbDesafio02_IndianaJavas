package indiana.javas.msauthorization.common;

import indiana.javas.msauthorization.entities.Role;
import indiana.javas.msauthorization.entities.User;
import indiana.javas.msauthorization.enums.ERole;

import java.util.Set;

public class Constants {

    public static final User VALID_USER = User.builder()
            .firstName("Juca")
            .lastName("Bala")
            .email("juca@bala.com")
            .password("segura12")
            .build();

    public static User ADMIN_USER = User.builder()
            .firstName("ADMIN")
            .lastName("ADMIN")
            .password("admin123")
            .email("admin@mail.com")
            .roles(Set.of(new Role(ERole.ROLE_ADMIN), new Role(ERole.ROLE_OPERATOR)))
            .build();

    public static User JORGE_BAGRE = User.builder()
            .firstName("Jorge")
            .lastName("Bagre")
            .email("jorge@bagre.com")
            .password("segura12")
            .roles(Set.of(new Role(1L, ERole.ROLE_OPERATOR)))
            .build();
}
