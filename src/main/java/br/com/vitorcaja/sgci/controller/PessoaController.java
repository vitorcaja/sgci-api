package br.com.vitorcaja.sgci.controller;

import br.com.vitorcaja.sgci.controller.schema.PessoaRequest;
import br.com.vitorcaja.sgci.controller.schema.PessoaResponse;
import br.com.vitorcaja.sgci.manager.PessoaManager;
import br.com.vitorcaja.sgci.model.Pessoa;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pessoas")
public class PessoaController {

    @Autowired
    private PessoaManager pessoaManager;

    // create
    @PostMapping
    public ResponseEntity<Long> criarPessoa(@Valid @RequestBody PessoaRequest pessoaRequest){
        Pessoa pessoa = pessoaManager.criarPessoa(pessoaRequest);
        return ResponseEntity.ok(pessoa.getId());
    }

    // read
    @GetMapping
    public ResponseEntity<List<PessoaResponse>> listarTodasPessoas(){
        List<PessoaResponse> pessoas = pessoaManager.recuperarTodasPessoas();
        return ResponseEntity.ok(pessoas);
    }

    // update
//    @PutMapping

    // delete
    @DeleteMapping(path={"{id}"})
    public ResponseEntity<Long> deletarPessoa(@PathVariable Long id) throws Exception {
        pessoaManager.deletarPessoa(id);
        return ResponseEntity.ok().build();
    }
}
