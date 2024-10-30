package br.com.naveguard.naveguardBackend.infra.security;

import br.com.naveguard.naveguardBackend.infra.errors.TokenException;
import br.com.naveguard.naveguardBackend.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("12345678")
    private String secret;

    public String generate(Authentication authentication) {
        User usuario = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(usuario.getId().toString())
                .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                .withIssuer("Naveguard")
                .sign(algorithm);
    }


    public Long getSubject(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            String subject = JWT.require(algorithm)
                    .withIssuer("Naveguard")
                    .build()
                    .verify(token)
                    .getSubject();
            return Long.valueOf(subject);
        }catch (Exception e) {
            throw new TokenException("Token invalido ou expirado");
        }
    }
}
