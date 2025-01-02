package br.com.vitorcaja.sgci.controller.schema;

import br.com.vitorcaja.sgci.enums.EstadoCivilEnum;
import br.com.vitorcaja.sgci.enums.TipoPessoaEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PessoaRequest(
        Long id,
        @NotNull String nome,
        @NotNull TipoPessoaEnum tipo,
        @NotNull String documento,
        @NotNull String profissao,
        @NotNull EstadoCivilEnum estadoCivil,
        @NotNull EnderecoRequest endereco
){}
