package br.com.vitorcaja.sgci.mapper;

import br.com.vitorcaja.sgci.controller.schema.PessoaRequest;
import br.com.vitorcaja.sgci.controller.schema.PessoaResponse;
import br.com.vitorcaja.sgci.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PessoaMapper  implements GenericMapper<Pessoa, PessoaRequest>{
    @Autowired
    private EnderecoMapper enderecoMapper;

    @Override
    public PessoaRequest toRequest(Pessoa entity) {
        if (entity == null) {
            return null;
        }

        return
                PessoaRequest
                        .builder()
                        .id(entity.getId())
                        .nome(entity.getNome())
                        .tipo(entity.getTipo())
                        .documento(entity.getDocumento())
                        .profissao(entity.getProfissao())
                        .estadoCivil(entity.getEstadoCivil())
                        .endereco(enderecoMapper.toRequest(entity.getEndereco()))
                        .build();
    }

    public PessoaResponse toResponse(Pessoa entity) {
        if (entity == null) {
            return null;
        }

        return
                PessoaResponse
                        .builder()
                        .id(entity.getId())
                        .nome(entity.getNome())
                        .tipo(entity.getTipo())
                        .documento(entity.getDocumento())
                        .profissao(entity.getProfissao())
                        .estadoCivil(entity.getEstadoCivil())
                        .endereco(enderecoMapper.toResponse(entity.getEndereco()))
                        .build();
    }

    public List<PessoaResponse> toResponseToList(List<Pessoa> entity) {
        if (entity == null || entity.isEmpty()) {
            return null;
        }

        return entity.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public Pessoa toEntity(PessoaRequest pessoaRequest) {
        if (pessoaRequest == null) {
            return null;
        }

        return
                Pessoa
                        .builder()
                        .id(pessoaRequest.id())
                        .nome(pessoaRequest.nome())
                        .tipo(pessoaRequest.tipo())
                        .documento(pessoaRequest.documento())
                        .profissao(pessoaRequest.profissao())
                        .estadoCivil(pessoaRequest.estadoCivil())
                        .endereco(enderecoMapper.toEntity(pessoaRequest.endereco()))
                        .build();
    }
}