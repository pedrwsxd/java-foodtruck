package br.projeto.spring.services;

import br.projeto.spring.repository.UsuarioRepository;
import br.projeto.spring.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para autenticar um usuário
    public Usuario authenticate(String email, String senha) {
        // Busca o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(email);

        // Verifica se a senha é correta
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario;  // Usuário válido
        }
        return null;  // Usuário ou senha inválidos
    }
}

