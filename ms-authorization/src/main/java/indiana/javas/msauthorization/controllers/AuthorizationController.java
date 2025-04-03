package indiana.javas.msauthorization.controllers;


import indiana.javas.msauthorization.entities.AuthUser;
import indiana.javas.msauthorization.entities.dto.LoginResponseDTO;
import indiana.javas.msauthorization.entities.dto.UserDtoMapper;
import indiana.javas.msauthorization.entities.dto.UserLoginDTO;
import indiana.javas.msauthorization.services.JwtTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth")
public class AuthorizationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService tokenService;

    @PostMapping("/token")
    public ResponseEntity<LoginResponseDTO> token(@RequestBody @Valid UserLoginDTO userDto) {

        var userPassword = new UsernamePasswordAuthenticationToken(userDto.email(), userDto.password());
        var auth = authenticationManager.authenticate(userPassword);
        var user = ((AuthUser) auth.getPrincipal()).getUser();

        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(UserDtoMapper.toLoginResponseDTO(token, user));
    }
}
