package net.sijbers.csia.reservationApp;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//https://ordina-jworks.github.io/security/2019/08/22/Securing-Web-Applications-With-Keycloak.html


@SpringBootApplication
@EnableSwagger2
@EnableAsync
public class ReservationAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationAppApplication.class, args);
	}
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.sijbers.csia.reservationApp.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Reservation App",
                "ComputerScience IA",
                "1.0",
                "http://nasdubai.com",
                new Contact("Tom","http://nasdubai.com","tom@sijbers.net"),
                "license",
                "http://nasdubai.com",
                Collections.emptyList()
        );
    } 
}
