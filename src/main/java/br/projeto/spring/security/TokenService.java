package br.projeto.spring.security;

import br.projeto.spring.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    // Geração de token JWT com informações do usuário, incluindo o papel (role)
    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Obtém os papéis (roles) do usuário e transforma em uma string separada por vírgula
            Set<String> roles = usuario.getRoles()
                    .stream()
                    .map(role -> role.getNome()) // Pega o nome do papel (ROLE_ADMIN ou ROLE_CLIENTE)
                    .collect(Collectors.toSet());

            // Gera o token JWT com o email e os papéis do usuário
            return JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(usuario.getEmail())
                    .withClaim("roles", String.join(",", roles)) // Inclui os papéis no token
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao tentar gerar token", exception);
        }
    }

    // Validação do token JWT, verificando o email e os papéis
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return null; // Retorna null se o token for inválido
        }
    }

    // Gera a data de expiração do token (2 horas a partir da criação)
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    // Extrai os papéis do token
    public Set<String> getRolesFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return Set.of(JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getClaim("roles").asString().split(","));
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Erro ao tentar extrair roles do token", exception);
        }
    }
}
