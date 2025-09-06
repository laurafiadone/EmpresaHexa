package Hexa.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"Hexa.example.demo", "application.adaptador", "application.controller", "domain.service", "dto", "infrastructure"})
@EnableJpaRepositories(basePackages = "infrastructure")
@EntityScan(basePackages = "infrastructure")
public class EmpresaHexaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpresaHexaApplication.class, args);
    }

}
