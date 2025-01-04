package br.com.vitorcaja.sgci.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenApi(){
        return
                new OpenAPI()
                        .info(
                                new Info()
                                        .title("SGCI - Sistema Gerenciador de Cadastro Imobiliário")
                                        .version("1.0.0"));
    }
}
