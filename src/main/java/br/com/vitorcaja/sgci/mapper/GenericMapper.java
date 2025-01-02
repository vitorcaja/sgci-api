package br.com.vitorcaja.sgci.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface GenericMapper<E, D> {
    D toReq(E entity);

    E toEntity(D req);

    default List<D> toReqList(List<E> entities) {
        return entities.stream().map(this::toReq).collect(Collectors.toList());
    }

    default List<E> toEntityList(List<D> reqs) {
        return reqs.stream().map(this::toEntity).collect(Collectors.toList());
    }
}