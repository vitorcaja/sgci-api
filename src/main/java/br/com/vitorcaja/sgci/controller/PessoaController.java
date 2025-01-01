package br.com.vitorcaja.sgci.controller;

import br.com.vitorcaja.sgci.controller.schema.PessoaReq;
import br.com.vitorcaja.sgci.manager.PessoaManager;
import br.com.vitorcaja.sgci.model.Pessoa;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pessoas")
public class PessoaController {

    @Autowired
    private PessoaManager pessoaManager;

    // create
    @PostMapping
    public ResponseEntity<Long> criarPessoa(@Valid @RequestBody PessoaReq pessoaReq){
        Pessoa pessoa = pessoaManager.criarPessoa(pessoaReq);
        return ResponseEntity.ok(pessoa.getId());
    }

    // read
//    @GetMapping
//    public ResponseEntity<String> findAll(){
//        return ResponseEntity.ok("Ok!");
//    }

    // update
//    @PutMapping

    // delete
    @DeleteMapping(path={"{id}"})
    public ResponseEntity<Long> deletarPessoa(@PathVariable Long id) throws Exception {
        pessoaManager.deletarPessoa(id);
        return ResponseEntity.ok().build();
    }
}
