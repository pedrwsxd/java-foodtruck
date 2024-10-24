package br.projeto.spring.security;

import br.projeto.spring.repository.RoleRepository;
import br.projeto.spring.roles.Role;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void loadRoles() {
        // Verificar se o papel ROLE_ADMIN já existe
        Optional<Role> adminRole = roleRepository.findByNome("ROLE_ADMIN");
        if (adminRole.isEmpty()) {
            // Se não existir, cria e salva no banco de dados
            Role roleAdmin = new Role("ROLE_ADMIN");
            roleRepository.save(roleAdmin);
        }

        // Verificar se o papel ROLE_CLIENTE já existe
        Optional<Role> clienteRole = roleRepository.findByNome("ROLE_CLIENTE");
        if (clienteRole.isEmpty()) {
            // Se não existir, cria e salva no banco de dados
            Role roleCliente = new Role("ROLE_CLIENTE");
            roleRepository.save(roleCliente);
        }
    }
}