package br.projeto.spring.security;

import br.projeto.spring.usuario.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Injeta a chave secreta do arquivo de configuração
    @Value("${jwt.secret}")
    private String secretKey;

    // Método para gerar o token JWT
    public String generateToken(Usuario usuario) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes()); // Converte a string em bytes

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("role", usuario.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS256) // Usa a chave gerada
                .compact();
    }

    // Método para extrair o nome de usuário do token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Método para extrair as Claims do token
    private Claims extractClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes()); // Converte a string em bytes
        return Jwts.parserBuilder()
                .setSigningKey(key) // Usa a chave gerada
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Método para verificar se o token está expirado
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Método para validar o token
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}