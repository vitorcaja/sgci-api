package br.com.vitorcaja.sgci.controller.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResponse <T>{
    private Long totalRecords;
    private int totalPages;
    private int pageSize;

    private List<T> registros;
}
