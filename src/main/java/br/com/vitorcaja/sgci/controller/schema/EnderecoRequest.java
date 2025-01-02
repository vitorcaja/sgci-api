package br.com.vitorcaja.sgci.controller.schema;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record EnderecoRequest(
        Long id,
        String cep,
        @NotNull String estado,
        @NotNull String cidade,
        String rua,
        String bairro,
        Integer numero
){}
