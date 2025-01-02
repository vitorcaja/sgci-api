package br.com.vitorcaja.sgci.manager;

import br.com.vitorcaja.sgci.controller.schema.PessoaRequest;
import br.com.vitorcaja.sgci.controller.schema.PessoaResponse;
import br.com.vitorcaja.sgci.mapper.PessoaMapper;
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

    public PessoaResponse recuperarPessoa(Long id){
        var pessoa =
                pessoaRepository
                        .findById(id)
                        .orElseThrow(()->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        String.format(
                                                "Falha ao recuperar pessoa. Pessoa com identificador %s não localizado.",
                                                id)));

        return pessoaMapper.toResponse(pessoa);
    }

    public List<PessoaResponse> recuperarTodasPessoas(){
        return recuperarTodasPessoasOrdenadas(Sort.Direction.ASC, "nome");
    }

    public List<PessoaResponse> recuperarTodasPessoasOrdenadas(Sort.Direction sortDirection, String nomeCampoParaOrdernar){
        var pessoas =  pessoaRepository.findAll(Sort.by(sortDirection, nomeCampoParaOrdernar));
        return pessoaMapper.toResponseToList(pessoas);
    }

    @Transactional
    public PessoaResponse criarPessoa(PessoaRequest pessoaRequest){

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
        pessoa = pessoaRepository.save(pessoa);

        return pessoaMapper.toResponse(pessoa);
    }

    @Transactional
    public PessoaResponse atualizarPessoa(PessoaRequest pessoaRequest){

        // validacoes
        if(pessoaRequest.id()==null || pessoaRequest.id().equals(0L)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format(
                            "Falha ao atualizar a pessoa %s. Pessoa com identificador %s não localizado",
                            pessoaRequest.nome(),
                            pessoaRequest.toString()));
        }

        if(pessoaRequest.endereco()==null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format(
                            "Falha ao atualizar a pessoa %s. O endereço está vazio. %s",
                            pessoaRequest.nome(),
                            pessoaRequest.toString()));
        }

        var pessoaBD =
                pessoaRepository
                        .findById(pessoaRequest.id())
                        .orElseThrow(()->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        String.format(
                                                "Falha ao atualizar pessoa. Pessoa com identificador %s não localizado.",
                                                pessoaRequest.id())));

        var pessoa = pessoaMapper.toEntity(pessoaRequest);
        pessoa.getEndereco().setId(pessoaBD.getId());

        pessoa = pessoaRepository.save(pessoa);

        return pessoaMapper.toResponse(pessoa);
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
                                                "Falha ao deletar pessoa. Pessoa com identificador %s não localizado.",
                                                id)));

        pessoaRepository.delete(pessoa);
    }
}
