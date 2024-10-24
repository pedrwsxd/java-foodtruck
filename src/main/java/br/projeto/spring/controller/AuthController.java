package br.projeto.spring.controller;

import br.projeto.spring.dto.LoginRequestDTO;
import br.projeto.spring.dto.RegisterRequestDTO;
import br.projeto.spring.dto.ResponseDTO;
import br.projeto.spring.repository.RoleRepository;
import br.projeto.spring.repository.UsuarioRepository;
import br.projeto.spring.security.TokenService;
import br.projeto.spring.usuario.Usuario;
import br.projeto.spring.roles.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        Usuario usuario = this.usuarioRepository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(body.senha(), usuario.getSenha())) {
            String token = this.tokenService.generateToken(usuario);
            return ResponseEntity.ok(new ResponseDTO(usuario.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional<Usuario> usuarioOptional = this.usuarioRepository.findByEmail(body.email());

        if (usuarioOptional.isEmpty()) {
            // Cria um novo usuário
            Usuario newUsuario = new Usuario();
            newUsuario.setSenha(passwordEncoder.encode(body.senha()));
            newUsuario.setEmail(body.email());
            newUsuario.setNome(body.nome());

            // Atribui o papel de CLIENTE por padrão
            Role clienteRole = roleRepository.findByNome("ROLE_CLIENTE")
                    .orElseThrow(() -> new RuntimeException("Default role CLIENTE not found"));
            newUsuario.setRoles(Set.of(clienteRole));

            this.usuarioRepository.save(newUsuario);

            String token = this.tokenService.generateToken(newUsuario);
            return ResponseEntity.ok(new ResponseDTO(newUsuario.getNome(), token));
        }

        return ResponseEntity.badRequest().build();
    }
}
