package br.com.vitorcaja.sgci.manager;

import br.com.vitorcaja.sgci.controller.schema.PessoaRequest;
import br.com.vitorcaja.sgci.controller.schema.PessoaResponse;
import br.com.vitorcaja.sgci.mapper.PessoaMapper;
import br.com.vitorcaja.sgci.model.Pessoa;
import br.com.vitorcaja.sgci.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Validated
public class PessoaManager {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private PessoaMapper pessoaMapper;

    public List<PessoaResponse> recuperarTodasPessoas(){
        return recuperarTodasPessoasOrdenadas(Sort.Direction.ASC, "nome");
    }

    public List<PessoaResponse> recuperarTodasPessoasOrdenadas(Sort.Direction sortDirection, String nomeCampoParaOrdernar){
        var pessoas =  pessoaRepository.findAll(Sort.by(sortDirection, nomeCampoParaOrdernar));
        return pessoaMapper.toResponseToList(pessoas);
    }

    @Transactional
    public Pessoa criarPessoa(PessoaRequest pessoaRequest){

        // validacoes
        if(pessoaRequest.endereco()==null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format(
                            "Falha ao criar a pessoa %s. O endereço está vazio. %s",
                            pessoaRequest.nome(),
                            pessoaRequest.toString()));
        }

        // criar uma pessoa de salvar no banco de dados
        var pessoa = pessoaMapper.toEntity(pessoaRequest);
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
