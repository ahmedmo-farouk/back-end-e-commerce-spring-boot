package back_end.e_commerce.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
import back_end.e_commerce.model.LocalUser;
import back_end.e_commerce.config.SecurityConstants;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;

    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    // public String generateJWT(LocalUser user) {
    //     return JWT.create()
    //             .withClaim(SecurityConstants.USERNAME_KEY, user.getUsername())
    //             .withExpiresAt(new Date(System.currentTimeMillis() + expiryInSeconds * 1000))
    //             .withIssuer(issuer)
    //             .sign(algorithm);
    // }
    public String generateJWT(LocalUser user) {
    return JWT.create()
            .withClaim("userId", user.getId())
            .withExpiresAt(new Date(System.currentTimeMillis() + expiryInSeconds * 1000))
            .withIssuer(issuer)
            .sign(algorithm);
}

    public String getUsername(String token) {
        return JWT.decode(token).getClaim(SecurityConstants.USERNAME_KEY).asString();
    }
}
