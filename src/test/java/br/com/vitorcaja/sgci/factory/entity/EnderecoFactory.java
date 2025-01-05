package br.com.vitorcaja.sgci.factory.entity;

import br.com.vitorcaja.sgci.controller.schema.EnderecoRequest;
import br.com.vitorcaja.sgci.model.Endereco;

public class EnderecoFactory {

    public static Endereco getEndereco(){
        return
                Endereco
                        .builder()
                        .cep("72135030")
                        .estado("DF")
                        .cidade("Brasília")
                        .bairro("Taguatinga Norte")
                        .rua("QI 03")
                        .numero(3)
                        .build();
    }

    public static EnderecoRequest getEnderecoRequest(){
        return
                EnderecoRequest
                        .builder()
                        .cep("72135030")
                        .estado("DF")
                        .cidade("Brasília")
                        .bairro("Taguatinga Norte")
                        .rua("QI 03")
                        .numero(3)
                        .build();
    }

    public static EnderecoRequest getEnderecoRequestForUpdate(){
        return
                EnderecoRequest
                        .builder()
                        .cep("72156204")
                        .estado("DF")
                        .cidade("Brasília")
                        .bairro("Taguatinga Norte")
                        .rua("QNL 12")
                        .numero(12)
                        .build();
    }
}
