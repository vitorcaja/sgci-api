package br.com.vitorcaja.sgci.repository;

import br.com.vitorcaja.sgci.model.Pessoa;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Page<Pessoa> findAll(Specification<Pessoa> filtrosSpecification, Pageable pageable);

    Optional<Pessoa> findPessoaByDocumento(@NotNull @Size(max = 255) String documento);
}
