package br.projeto.spring.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DadosEndereco(

        @NotBlank
        @Size(max = 255)
        String logradouro,

        @NotBlank
        @Size(max = 255)
        String bairro,

        @NotBlank
        @Pattern(regexp = "\\d{5}-\\d{3}")
        String cep,

        @NotBlank
        @Size(max = 20)
        String numero,

        @Size(max = 255)
        String complemento,

        @NotBlank
        @Size(max = 255)
        String cidade
) {


}
