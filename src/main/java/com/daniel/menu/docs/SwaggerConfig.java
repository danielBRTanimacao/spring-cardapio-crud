package com.daniel.menu.docs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(this.info())
                .servers(List.of(this.serverLocal()));
    }

    private Info info() {
        return new Info()
                .title("Menu CRUD DOCS")
                .description("""
                            Documentando um simples CRUD de um cardapio. Adicionar, deletar e ler varias tipos de comidas
                            no banco de dados
                            """)
                .version("0.0.1");
    }
    private Server serverLocal() {
        return new Server()
                .url("http://localhost:8080")
                .description("Servidor local");
    }
}
