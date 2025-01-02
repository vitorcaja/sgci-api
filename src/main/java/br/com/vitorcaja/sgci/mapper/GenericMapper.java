package br.com.vitorcaja.sgci.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface GenericMapper<E, D> {
    D toRequest(E entity);

    E toEntity(D req);

    default List<D> toRequestList(List<E> entities) {
        return entities.stream().map(this::toRequest).collect(Collectors.toList());
    }

    default List<E> toEntityList(List<D> reqs) {
        return reqs.stream().map(this::toEntity).collect(Collectors.toList());
    }
}