package br.com.vitorcaja.sgci.controller.schema;

import br.com.vitorcaja.sgci.enums.TipoPessoaEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class PessoaFilter extends  FilterPageable{
    public String nome;
    public TipoPessoaEnum tipo;
    public String documento;
    public String cep;
    public String estado;
    public String cidade;
}