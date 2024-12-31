package br.com.vitorcaja.sgci.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ENDERECO")
@Data
@AllArgsConstructor @NoArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENDERECO")
    private Long id;

    @Size(max = 8)
    @Column(name = "CEP")
    private String cep;

    @NotNull
    @Size(max = 255)
    @Column(name = "ESTADO")
    private String estado;

    @NotNull
    @Size(max = 255)
    @Column(name = "CIDADE")
    private String cidade;

    @Size(max = 255)
    @Column(name = "RUA")
    private String rua;

    @Size(max = 255)
    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "NUMERO")
    private Integer numero;
}
