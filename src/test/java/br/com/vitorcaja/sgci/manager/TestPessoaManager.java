package br.com.vitorcaja.sgci.manager;

import br.com.vitorcaja.sgci.controller.schema.PagedResponse;
import br.com.vitorcaja.sgci.controller.schema.PessoaResponse;
import br.com.vitorcaja.sgci.factory.entity.EnderecoFactory;
import br.com.vitorcaja.sgci.factory.entity.PessoaFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

@ActiveProfiles("test")
@SpringBootTest
public class TestPessoaManager {
    @Autowired
    private PessoaManager pessoaManager;
    private PessoaResponse pessoaResponse;

    @BeforeEach
    void setUp(){
        var enderecoRequest = EnderecoFactory.getEnderecoRequest();
        var pessoaRequest = PessoaFactory.getPessoaRequest(enderecoRequest);
        pessoaResponse = pessoaManager.criarPessoa(pessoaRequest);
    }

    // create pessoa
    @Test
    void criarPessoa(){
        Assertions.assertNotNull(pessoaResponse);
        Assertions.assertNotNull(pessoaResponse.id());
    }

    // findById pessoa
    @Test
    void recuperarPessoa(){
        var pessoa = pessoaManager.recuperarPessoa(pessoaResponse.id());

        Assertions.assertNotNull(pessoa);
        Assertions.assertNotNull(pessoa.id());
        Assertions.assertEquals(pessoaResponse.documento(), pessoa.documento());
    }

    // findAllPaged
    @Test
    void recuperarPessoasPaged() {
        var pessoaFilter = PessoaFactory.getPessoaFilter();
        PagedResponse<PessoaResponse> pessoasPaged = pessoaManager.recuperarTodasPessoas(pessoaFilter);
        Assertions.assertFalse(pessoasPaged.getRegistros().isEmpty() && pessoasPaged.getTotalPages()<1);
    }

    @Test
    void recuperarPessoasPagedByFilterCep() {
        var pessoaFilter = PessoaFactory.getPessoaFilter();
        pessoaFilter.setCep("72135030");
        PagedResponse<PessoaResponse> pessoasPaged = pessoaManager.recuperarTodasPessoas(pessoaFilter);
        Assertions.assertFalse(pessoasPaged.getRegistros().isEmpty() && pessoasPaged.getTotalPages()<1);
    }

    // delete pessoa
    @Test
    void excluirPessoa() throws Exception {
        var idPessoa = pessoaResponse.id();

        pessoaManager.deletarPessoa(pessoaResponse.id());

        // Quando tentar recuperar uma pessoa com um ID inexistente, esperamos uma exceção
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            pessoaManager.recuperarPessoa(idPessoa);
        });

        // Verifica se a exceção é do tipo esperado e tem o status correto
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        // Verifica se a mensagem da exceção contém a informação correta
        String expectedMessage = String.format("Falha ao recuperar pessoa. Pessoa com identificador %s não localizado.", idPessoa);
        Assertions.assertEquals(expectedMessage, exception.getReason());
    }

    // update pessoa
    @Test
    void atualizarPessoa() throws Exception {
        var enderecoRequestParaAtualizar = EnderecoFactory.getEnderecoRequestForUpdate();
        var pessoaRequestParaAtualizar = PessoaFactory.getPessoaRequestForUpdate(enderecoRequestParaAtualizar, pessoaResponse.id(), pessoaResponse.documento());
        var pessoaResponseAtualizado = pessoaManager.atualizarPessoa(pessoaRequestParaAtualizar);

        Assertions.assertEquals(pessoaResponseAtualizado.id(), this.pessoaResponse.id());
        Assertions.assertNotEquals(pessoaResponseAtualizado.nome(), this.pessoaResponse.nome());
        Assertions.assertNotEquals(pessoaResponseAtualizado.profissao(), this.pessoaResponse.profissao());
        Assertions.assertNotEquals(pessoaResponseAtualizado.tipo(), this.pessoaResponse.tipo());
        Assertions.assertNotEquals(pessoaResponseAtualizado.endereco().cep(), this.pessoaResponse.endereco().cep());
        Assertions.assertNotEquals(pessoaResponseAtualizado.endereco().rua(), this.pessoaResponse.endereco().rua());
        Assertions.assertNotEquals(pessoaResponseAtualizado.endereco().numero(), this.pessoaResponse.endereco().numero());
    }





}
