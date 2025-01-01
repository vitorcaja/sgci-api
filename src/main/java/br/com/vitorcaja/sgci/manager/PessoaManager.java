package br.com.vitorcaja.sgci.manager;

import br.com.vitorcaja.sgci.controller.schema.PessoaReq;
import br.com.vitorcaja.sgci.model.Endereco;
import br.com.vitorcaja.sgci.model.Pessoa;
import br.com.vitorcaja.sgci.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

@Service
@Validated
public class PessoaManager {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
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

    @Transactional
    public void deletarPessoa(Long id) throws Exception {
        var pessoa =
                pessoaRepository
                        .findById(id)
                        .orElseThrow(()->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        String.format(
                                                "Falha ao deletar pessoa. Registro com id %s não localizado.",
                                                id)));

        pessoaRepository.delete(pessoa);
    }
}
