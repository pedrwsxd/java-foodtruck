package br.projeto.spring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(

        @NotBlank
        @Size(max = 255)
        String nome,

        @NotBlank
        @Size(max = 20)
        String telefone,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 6, max = 255)
        String senha
) {

}
