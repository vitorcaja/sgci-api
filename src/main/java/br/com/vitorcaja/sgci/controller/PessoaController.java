package br.com.vitorcaja.sgci.controller;

import br.com.vitorcaja.sgci.controller.schema.PagedResponse;
import br.com.vitorcaja.sgci.controller.schema.PessoaFilter;
import br.com.vitorcaja.sgci.controller.schema.PessoaRequest;
import br.com.vitorcaja.sgci.controller.schema.PessoaResponse;
import br.com.vitorcaja.sgci.manager.PessoaManager;
import br.com.vitorcaja.sgci.model.Pessoa;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Pessoa::Pessoa")
@RestController
@RequestMapping("pessoas")
public class PessoaController {

    @Autowired
    private PessoaManager pessoaManager;

    // create
    @PostMapping
    public ResponseEntity<Long> criarPessoa(@Valid @RequestBody PessoaRequest pessoaRequest){
        PessoaResponse pessoa = pessoaManager.criarPessoa(pessoaRequest);
        return ResponseEntity.ok(pessoa.id());
    }

    // read
    @GetMapping
    public ResponseEntity<PagedResponse<PessoaResponse>> listarTodasPessoas(@Valid PessoaFilter pessoaFilter){
        PagedResponse<PessoaResponse> pessoasPaged = pessoaManager.recuperarTodasPessoas(pessoaFilter);
        return ResponseEntity.ok(pessoasPaged);
    }

    @GetMapping(path={"{id}"})
    public ResponseEntity<PessoaResponse> recuperarPessoa(@PathVariable @NotNull Long id){
        PessoaResponse pessoa = pessoaManager.recuperarPessoa(id);
        return ResponseEntity.ok(pessoa);
    }

    // update
    @PutMapping
    public ResponseEntity<Long> atualizarPessoa(@Valid @RequestBody PessoaRequest pessoaRequest){
        PessoaResponse pessoaResponse = pessoaManager.atualizarPessoa(pessoaRequest);
        return ResponseEntity.ok(pessoaResponse.id());
    }

    // delete
    @DeleteMapping(path={"{id}"})
    public ResponseEntity<Long> deletarPessoa(@PathVariable Long id) throws Exception {
        pessoaManager.deletarPessoa(id);
        return ResponseEntity.ok().build();
    }
}
