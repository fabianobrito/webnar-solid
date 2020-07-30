package br.com.webnar.solid.currentaccountmanagement.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${project.version}")
    private String version;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("br.com.webnar.solid.currentaccountmanagement.controllers"))
                .paths(PathSelectors.any())
                .build().directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalDateTime.class, String.class).apiInfo(apiInfo())
                .tags(new Tag("Conta", "Rest API para gerenciar serviços de conta bancária do correntista"),
                        new Tag("Cliente", "Rest API para gerenciar serviços de dados do cliente"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("API Poc para workshop SOLID")
                .description("API de exemplo para o workshop de Solid.")
                .version(this.version).license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.com/licenses/LICENSE-2.0").build();
    }
}
