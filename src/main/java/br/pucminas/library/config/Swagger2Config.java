package br.pucminas.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.basePackage("br.pucminas.library.controllers"))
			.build()
			.apiInfo(apiInfo())
			.tags(
				new Tag("Books", "Endpoint para o gerenciamento do acervo."),
				new Tag("Comments", "Endpoint para o gerenciamento dos comentários em livros do acervo."),
				new Tag("Basket", "Endpoint para o gerenciamento dos carrinhos de compras."));
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("REST API para a livraria da Amazon.")
			.description("REST API para a livraria da Amazon.")
			.version("1.0.0")
			.license("Apache License Version 2.0")
			.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
			.build();
	}
}
