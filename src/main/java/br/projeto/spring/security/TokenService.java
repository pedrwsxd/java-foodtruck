package br.projeto.spring.security;

import br.projeto.spring.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.web.OffsetScrollPositionHandlerMethodArgumentResolver;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final OffsetScrollPositionHandlerMethodArgumentResolver offsetResolver;

    @Value("${api.security.token.secret}")
    private  String secret;

    public TokenService(OffsetScrollPositionHandlerMethodArgumentResolver offsetResolver) {
        this.offsetResolver = offsetResolver;
    }

    public String generateToken(Usuario usuario) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(this.genereteExpirationDate())
                    .sign(algorithm);
            return token;

        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao tentar gerar token");
        }
    }

    public String validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();


        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private Instant genereteExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}

