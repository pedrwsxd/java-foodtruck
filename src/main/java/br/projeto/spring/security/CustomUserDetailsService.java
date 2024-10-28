package br.projeto.spring.security;

import br.projeto.spring.repository.UsuarioRepository;
import br.projeto.spring.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = this.usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        Collection<GrantedAuthority> authorities = mapRolesToAuthorities(usuario.getRoles());


        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getSenha(),
                authorities
        );
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Set<br.projeto.spring.roles.Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getNome()))
                .collect(Collectors.toList());
    }
}
