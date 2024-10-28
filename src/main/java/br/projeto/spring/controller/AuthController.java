package br.projeto.spring.controller;

import br.projeto.spring.dto.LoginRequestDTO;
import br.projeto.spring.dto.RegisterRequestDTO;
import br.projeto.spring.dto.ResponseDTO;
import br.projeto.spring.repository.RoleRepository;
import br.projeto.spring.repository.UsuarioRepository;
import br.projeto.spring.roles.Role;
import br.projeto.spring.security.TokenService;
import br.projeto.spring.usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
        Usuario usuario = this.usuarioRepository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(body.senha(), usuario.getSenha())) {
            String token = this.tokenService.generateToken(usuario);


            Set<String> roles = usuario.getRoles().stream()
                    .map(Role::getNome)
                    .collect(Collectors.toSet());

            return ResponseEntity.ok(new ResponseDTO(usuario.getNome(), token, roles, usuario.getId()));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        Optional<Usuario> usuarioOptional = this.usuarioRepository.findByEmail(body.email());

        if (usuarioOptional.isEmpty()) {

            Usuario newUsuario = new Usuario();
            newUsuario.setSenha(passwordEncoder.encode(body.senha()));
            newUsuario.setEmail(body.email());
            newUsuario.setNome(body.nome());
            newUsuario.setTelefone(body.telefone()); // Define o telefone

            Role clienteRole = roleRepository.findByNome("ROLE_CLIENTE")
                    .orElseThrow(() -> new RuntimeException("Default role CLIENTE not found"));
            newUsuario.setRoles(Set.of(clienteRole));

            this.usuarioRepository.save(newUsuario);

            String token = this.tokenService.generateToken(newUsuario);

            Set<String> roles = newUsuario.getRoles().stream()
                    .map(Role::getNome)
                    .collect(Collectors.toSet());

            return ResponseEntity.ok(new ResponseDTO(newUsuario.getNome(), token, roles, newUsuario.getId()));
        }

        return ResponseEntity.badRequest().build();
    }
}
