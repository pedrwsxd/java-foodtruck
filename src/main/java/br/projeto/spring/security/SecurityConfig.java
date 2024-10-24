package br.projeto.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Acesso público
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/produtos/listar").permitAll()  // Ex: Listar produtos

                        // Permissões baseadas em papéis
                        .requestMatchers(HttpMethod.POST, "/produtos/**").hasRole("ADMIN")  // Ex: Adicionar produto (somente ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/produtos/**").hasRole("ADMIN")   // Ex: Atualizar produto (somente ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("ADMIN") // Ex: Remover produto (somente ADMIN)
                        .requestMatchers(HttpMethod.GET, "/pedidos/**").hasAnyRole("ADMIN", "CLIENTE") // Ex: Visualizar pedidos
                        .requestMatchers(HttpMethod.POST, "/pedidos/**").hasRole("CLIENTE") // Criar pedidos (somente CLIENTE)

                        // Qualquer outra requisição precisa ser autenticada
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
