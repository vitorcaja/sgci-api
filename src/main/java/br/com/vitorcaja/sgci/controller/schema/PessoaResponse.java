package br.com.vitorcaja.sgci.controller.schema;

import br.com.vitorcaja.sgci.enums.EstadoCivilEnum;
import br.com.vitorcaja.sgci.enums.TipoPessoaEnum;
import lombok.Builder;

@Builder
public record PessoaResponse (
        Long id,
        String nome,
        TipoPessoaEnum tipo,
        String documento,
        String profissao,
        EstadoCivilEnum estadoCivil,
        EnderecoResponse endereco
){}
