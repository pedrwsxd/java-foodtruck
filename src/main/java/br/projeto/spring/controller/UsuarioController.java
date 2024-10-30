package br.projeto.spring.controller;

import br.projeto.spring.repository.UsuarioRepository;
import br.projeto.spring.roles.Role;
import br.projeto.spring.security.TokenService;
import br.projeto.spring.usuario.DadosAtualizacaoUsuario;
import br.projeto.spring.usuario.DadosCadastroUsuario;
import br.projeto.spring.usuario.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("sucesso!");
    }

    @PostMapping("/add")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody DadosCadastroUsuario dados) {
        Usuario usuario = new Usuario(dados);
        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Usuario> obterUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/atualizar/{id}")

    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody DadosAtualizacaoUsuario dados, HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization").substring(7);
            Long usuarioAutenticadoId = tokenService.getIdFromToken(token);

            if (!id.equals(usuarioAutenticadoId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Obter as roles do usuário autenticado a partir do token JWT
            Set<String> roles = tokenService.getRolesFromToken(token);

            // Verificar se o usuário está tentando atualizar as roles e se ele é admin
            if (dados.roles() != null && !dados.roles().isEmpty() &&
                    !dados.roles().equals(usuario.getRoles().stream().map(Role::getNome).collect(Collectors.toSet())) &&
                    !roles.contains("ROLE_ADMIN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            usuario.atualizarInformacoes(dados);
            usuarioRepository.save(usuario);
            return ResponseEntity.ok(usuario);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
