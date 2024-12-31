package br.com.vitorcaja.sgci.controller.schema;

import br.com.vitorcaja.sgci.enums.EstadoCivilEnum;
import br.com.vitorcaja.sgci.enums.TipoPessoaEnum;
import jakarta.validation.constraints.NotNull;

public record PessoaReq (
        @NotNull String nome,
        @NotNull TipoPessoaEnum tipo,
        @NotNull String documento,
        @NotNull String profissao,
        @NotNull EstadoCivilEnum estadoCivil,
        @NotNull EnderecoReq endereco
){}
