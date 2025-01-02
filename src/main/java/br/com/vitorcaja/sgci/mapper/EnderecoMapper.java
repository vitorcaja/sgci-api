package br.com.vitorcaja.sgci.mapper;

import br.com.vitorcaja.sgci.controller.schema.EnderecoRequest;
import br.com.vitorcaja.sgci.controller.schema.EnderecoResponse;
import br.com.vitorcaja.sgci.model.Endereco;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EnderecoMapper implements GenericMapper<Endereco, EnderecoRequest>{
    @Override
    public EnderecoRequest toRequest(Endereco entity) {
        if (entity == null) {
            return null;
        }

        return
                EnderecoRequest
                        .builder()
                        .id(entity.getId())
                        .cep(entity.getCep())
                        .estado(entity.getEstado())
                        .cidade(entity.getCidade())
                        .rua(entity.getRua())
                        .bairro(entity.getBairro())
                        .numero(entity.getNumero())
                        .build();
    }

    public EnderecoResponse toResponse(Endereco entity) {
        if (entity == null) {
            return null;
        }

        return
                EnderecoResponse
                        .builder()
                        .id(entity.getId())
                        .cep(entity.getCep())
                        .estado(entity.getEstado())
                        .cidade(entity.getCidade())
                        .rua(entity.getRua())
                        .bairro(entity.getBairro())
                        .numero(entity.getNumero())
                        .build();
    }

    public List<EnderecoResponse> toResponseToList(List<Endereco> entity) {
        if (entity == null || entity.isEmpty()) {
            return null;
        }

        return entity.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public Endereco toEntity(EnderecoRequest enderecoRequest) {
        if (enderecoRequest == null) {
            return null;
        }

        return
                Endereco
                        .builder()
                        .id(enderecoRequest.id())
                        .cep(enderecoRequest.cep())
                        .estado(enderecoRequest.estado())
                        .cidade(enderecoRequest.cidade())
                        .rua(enderecoRequest.rua())
                        .bairro(enderecoRequest.bairro())
                        .numero(enderecoRequest.numero())
                        .build();
    }
}