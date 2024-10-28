package br.projeto.spring.security;

import br.projeto.spring.repository.RoleRepository;
import br.projeto.spring.repository.UsuarioRepository;
import br.projeto.spring.roles.Role;
import br.projeto.spring.usuario.Usuario;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostConstruct
    public void loadRoles() {

        Optional<Role> adminRole = roleRepository.findByNome("ROLE_ADMIN");
        if (adminRole.isEmpty()) {

            Role roleAdmin = new Role("ROLE_ADMIN");
            roleRepository.save(roleAdmin);
        }

        Optional<Role> clienteRole = roleRepository.findByNome("ROLE_CLIENTE");
        if (clienteRole.isEmpty()) {

            Role roleCliente = new Role("ROLE_CLIENTE");
            roleRepository.save(roleCliente);
        }
    }


    @Bean
    CommandLineRunner createAdminIfNotExists() {
        return args -> {

            Optional<Role> roleAdmin = roleRepository.findByNome("ROLE_ADMIN");
            if (roleAdmin.isEmpty()) {
                Role adminRole = new Role("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }


            Optional<Usuario> adminUser = usuarioRepository.findByEmail("admin@admin.com");
            if (adminUser.isEmpty()) {

                Usuario admin = new Usuario();
                admin.setNome("Admin");
                admin.setEmail("admin@admin.com");
                admin.setSenha(passwordEncoder.encode("admin123")); // Define a senha padrão para admin
                
                Set<Role> roles = new HashSet<>();
                roles.add(roleRepository.findByNome("ROLE_ADMIN").get());
                admin.setRoles(roles);

                usuarioRepository.save(admin);
                System.out.println("Admin user created: admin@admin.com");
            } else {
                System.out.println("Admin user already exists");
            }
        };
    }
}
