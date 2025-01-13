package br.com.vitorcaja.sgci.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Permite requisições em qualquer URL
                .allowedOrigins("http://localhost:9000")  // Permite o frontend na porta 9000
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Adicione 'OPTIONS'
                .allowedHeaders("*")  // Permite todos os cabeçalhos
                .allowCredentials(true);  // Permite o envio de cookies, se necessário
    }
}
