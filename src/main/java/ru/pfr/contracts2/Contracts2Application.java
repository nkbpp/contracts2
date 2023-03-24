package ru.pfr.contracts2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.pfr.contracts2.config.audit.AuditorAwareImpl;


@SpringBootApplication
public class Contracts2Application {

    public static void main(String[] args) {
        SpringApplication.run(Contracts2Application.class, args);
    }

}

