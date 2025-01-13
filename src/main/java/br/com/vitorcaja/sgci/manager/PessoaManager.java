package br.com.vitorcaja.sgci.manager;

import br.com.vitorcaja.sgci.controller.schema.PagedResponse;
import br.com.vitorcaja.sgci.controller.schema.PessoaFilter;
import br.com.vitorcaja.sgci.controller.schema.PessoaRequest;
import br.com.vitorcaja.sgci.controller.schema.PessoaResponse;
import br.com.vitorcaja.sgci.mapper.PessoaMapper;
import br.com.vitorcaja.sgci.model.Pessoa;
import br.com.vitorcaja.sgci.repository.PessoaRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public PagedResponse<PessoaResponse> recuperarTodasPessoas(@Valid PessoaFilter pessoaFilter){
        Specification<Pessoa> filtrosSpecification = (root, query, cb) -> {
            List<Predicate> condicoes = new ArrayList<>();

            if(Strings.isNotBlank(pessoaFilter.getNome())){
                condicoes.add(
                        cb.like(
                                root.get("nome"),
                                String.format(
                                        "%%%s%%",
                                        pessoaFilter.getNome())));
            }

            if(Strings.isNotBlank(pessoaFilter.getDocumento())){
                condicoes.add(
                        cb.equal(
                                root.get("documento"),
                                pessoaFilter.getDocumento()));
            }

            if(pessoaFilter.getTipo()!=null){
                condicoes.add(
                        cb.equal(
                                root.get("tipo"),
                                pessoaFilter.getTipo()));
            }

            if(Strings.isNotBlank(pessoaFilter.getCep())){
                condicoes.add(
                        cb.equal(
                                root.get("endereco").get("cep"),
                                pessoaFilter.getCep()));
            }

            if(Strings.isNotBlank(pessoaFilter.getEstado())){
                condicoes.add(
                        cb.equal(
                                root.get("endereco").get("estado"),
                                pessoaFilter.getEstado()));
            }

            if(Strings.isNotBlank(pessoaFilter.getCidade())){
                condicoes.add(
                        cb.equal(
                                root.get("endereco").get("cidade"),
                                pessoaFilter.getCidade()));
            }

            return cb.and(condicoes.toArray(Predicate[]::new));
        };

        Pageable pageable =
                PageRequest.of(
                        pessoaFilter.getPage(),
                        pessoaFilter.getSize(),
                        Sort.by(
                                pessoaFilter.getDirection(),
                                pessoaFilter.getOrdenarPor()));

        var pessoas = pessoaRepository.findAll(filtrosSpecification, pageable);

        var pessoasResponse =
                pessoas
                        .stream()
                        .map(pessoa -> {
                            return pessoaMapper.toResponse(pessoa);
                        })
                        .collect(Collectors.toList());

        return
                new PagedResponse<PessoaResponse>(
                        pessoas.getTotalElements(),
                        pessoas.getTotalPages(),
                        pageable.getPageSize(),
                        pessoasResponse);
    }

    @Transactional
    public PessoaResponse criarPessoa(PessoaRequest pessoaRequest){

        // validacoes
        if(pessoaRequest.endereco()==null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format(
                            "Falha ao criar a pessoa '%s'. O endereço está vazio. %s",
                            pessoaRequest.nome(),
                            pessoaRequest.toString()));
        }

        if(pessoaRepository.findPessoaByDocumento(pessoaRequest.documento()).isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    String.format(
                            "Falha ao criar a pessoa '%s'. O documento '%s' já foi cadastrado.",
                            pessoaRequest.nome(),
                            pessoaRequest.documento()));
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
