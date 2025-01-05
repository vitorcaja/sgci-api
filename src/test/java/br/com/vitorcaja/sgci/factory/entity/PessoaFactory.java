package br.com.vitorcaja.sgci.factory.entity;

import br.com.vitorcaja.sgci.controller.schema.EnderecoRequest;
import br.com.vitorcaja.sgci.controller.schema.PessoaFilter;
import br.com.vitorcaja.sgci.controller.schema.PessoaRequest;
import br.com.vitorcaja.sgci.enums.EstadoCivilEnum;
import br.com.vitorcaja.sgci.enums.TipoPessoaEnum;
import br.com.vitorcaja.sgci.utils.CpfGerador;
import org.springframework.data.domain.Sort;

public class PessoaFactory {
    public static PessoaRequest getPessoaRequest(EnderecoRequest enderecoRequest){
        return
                PessoaRequest
                        .builder()
                        .nome("Vitor Cajá Mock")
                        .documento(gerarCpfValidoAleatorio())
                        .tipo(TipoPessoaEnum.PESSOA_FISICA)
                        .profissao("Analista de Sistemas")
                        .estadoCivil(EstadoCivilEnum.CASADO)
                        .endereco(enderecoRequest)
                        .build();
    }

    public static PessoaRequest getPessoaRequestForUpdate(EnderecoRequest enderecoRequest, Long id, String documento){
        return
                PessoaRequest
                        .builder()
                        .id(id)
                        .nome("Vitor Cajá Mock Jr.")
                        .documento(documento)
                        .tipo(TipoPessoaEnum.PESSOA_JURIDICA)
                        .profissao("Analista de Sistemas Jr.")
                        .estadoCivil(EstadoCivilEnum.SOLTEIRO)
                        .endereco(enderecoRequest)
                        .build();
    }

    public static PessoaFilter getPessoaFilter(){
        var pessoaFilter =
                PessoaFilter
                        .builder()
                        .build();

        pessoaFilter.setPage(0);
        pessoaFilter.setSize(1);
        pessoaFilter.setDirection(Sort.Direction.ASC);
        pessoaFilter.setOrdenarPor("nome");

        return pessoaFilter;
    }

    private static String gerarCpfValidoAleatorio(){
        return CpfGerador.gerarCpf();
    }
}
