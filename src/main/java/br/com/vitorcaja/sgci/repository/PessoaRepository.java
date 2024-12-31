package br.com.vitorcaja.sgci.repository;

import br.com.vitorcaja.sgci.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
