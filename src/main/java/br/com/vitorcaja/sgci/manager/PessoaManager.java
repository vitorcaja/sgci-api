package br.com.vitorcaja.sgci.manager;

import br.com.vitorcaja.sgci.controller.schema.PessoaReq;
import br.com.vitorcaja.sgci.model.Endereco;
import br.com.vitorcaja.sgci.model.Pessoa;
import br.com.vitorcaja.sgci.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaManager {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa criarPessoa(PessoaReq pessoaReq){

        // validacoes

        var endereco =
                Endereco
                        .builder()
                        .cep(pessoaReq.endereco().cep())
                        .estado(pessoaReq.endereco().estado())
                        .cidade(pessoaReq.endereco().cidade())
                        .bairro(pessoaReq.endereco().bairro())
                        .rua(pessoaReq.endereco().rua())
                        .numero(pessoaReq.endereco().numero())
                        .build();

        // criar uma pessoa de salvar no banco de dados
        var pessoa =
                Pessoa
                        .builder()
                        .nome(pessoaReq.nome())
                        .tipo(pessoaReq.tipo())
                        .documento(pessoaReq.documento())
                        .estadoCivil(pessoaReq.estadoCivil())
                        .profissao(pessoaReq.profissao())
                        .endereco(endereco)
                        .build();

        return pessoaRepository.save(pessoa);
    }
}
