package indiana.javas.msauthorization.entities.dto;

import indiana.javas.msauthorization.entities.Role;
import indiana.javas.msauthorization.entities.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDtoMapper {

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet())
        );
    }

    public static LoginResponseDTO toLoginResponseDTO(String token, User user) {
        return new LoginResponseDTO(
                token,
                user.getEmail(),
                user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet())
        );
    }

    public static User fromUserCreateDTO(UserCreateDTO userCreateDTO) {
        return User.builder()
                .firstName(userCreateDTO.firstName())
                .lastName(userCreateDTO.lastName())
                .email(userCreateDTO.email())
                .password(userCreateDTO.password())
                .build();
    }

    public static User fromUserUpdateDTO(UserUpdateDTO userUpdateDTO) {
        return User.builder()
                .firstName(userUpdateDTO.firstName())
                .lastName(userUpdateDTO.lastName())
                .email(userUpdateDTO.email())
                .password(userUpdateDTO.password())
                .build();
    }
}
