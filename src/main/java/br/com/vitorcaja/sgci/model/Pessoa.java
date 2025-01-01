package br.com.vitorcaja.sgci.model;

import br.com.vitorcaja.sgci.enums.EstadoCivilEnum;
import br.com.vitorcaja.sgci.enums.TipoPessoaEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PESSOA")
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PESSOA")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "NOME")
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "EN_TIPO")
    private TipoPessoaEnum tipo;

    @NotNull
    @Size(max = 255)
    @Column(name = "DOCUMENTO", unique = true)
    private String documento; // cpf | cnpj

    @NotNull
    @Size(max = 255)
    @Column(name = "TX_PROFISSAO")
    private String profissao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "EN_ESTADO_CIVIL")
    private EstadoCivilEnum estadoCivil;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ENDERECO")
    private Endereco endereco;
}
