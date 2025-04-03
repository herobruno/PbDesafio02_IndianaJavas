package indiana.javas.msauthorization.controllers;

import indiana.javas.msauthorization.entities.dto.UserCreateDTO;
import indiana.javas.msauthorization.entities.dto.UserDtoMapper;
import indiana.javas.msauthorization.entities.dto.UserResponseDTO;
import indiana.javas.msauthorization.entities.dto.UserUpdateDTO;
import indiana.javas.msauthorization.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserCreateDTO userDto) {
        var user = userService.save(UserDtoMapper.fromUserCreateDTO(userDto), userDto.roles());
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        var user = userService.findById(id);
        return ResponseEntity.ok(UserDtoMapper.toDTO(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO userDto) {
        userService.update(id, UserDtoMapper.fromUserUpdateDTO(userDto), userDto.roles());

        return ResponseEntity.noContent().build();
    }

}
