package br.projeto.spring.controllers;

import br.projeto.spring.security.JwtUtil;
import br.projeto.spring.services.AuthenticationService;
import br.projeto.spring.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        // Autentica o usuário
        Usuario authenticatedUser = authenticationService.authenticate(usuario.getEmail(), usuario.getSenha());

        if (authenticatedUser != null) {
            // Gera o token JWT
            return jwtUtil.generateToken(authenticatedUser);
        } else {
            // Se a autenticação falhar
            throw new RuntimeException("Usuário ou senha inválidos");
        }
    }
}
