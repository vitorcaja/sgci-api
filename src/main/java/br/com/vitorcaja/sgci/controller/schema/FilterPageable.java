package br.com.vitorcaja.sgci.controller.schema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class FilterPageable {
    @NotNull public Integer page;
    @NotNull public Integer size;
    @NotNull public Sort.Direction direction;
    @NotNull public String ordenarPor;
}
