package indiana.javas.msproducts.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    public String getUserEmailFromToken(String token) {
        try {
            return JWT.decode(token).getSubject();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
