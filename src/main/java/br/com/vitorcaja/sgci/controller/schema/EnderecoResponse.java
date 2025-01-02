package br.com.vitorcaja.sgci.controller.schema;

import lombok.Builder;

@Builder
public record EnderecoResponse(
        Long id,
        String cep,
        String estado,
        String cidade,
        String rua,
        String bairro,
        Integer numero
){}
