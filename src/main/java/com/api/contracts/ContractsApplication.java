package com.api.contracts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EntityScan("com.api.contracts.entities")
@ComponentScan({"com.api.contracts.services",
        "com.api.contracts.configurations",
        "com.api.contracts.controllers"})
@EnableJpaRepositories("com.api.contracts.repositories")
public class ContractsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContractsApplication.class, args);
    }

}
