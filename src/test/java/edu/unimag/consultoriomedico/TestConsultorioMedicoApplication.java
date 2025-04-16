package edu.unimag.consultoriomedico;

import org.springframework.boot.SpringApplication;

public class TestConsultorioMedicoApplication {

    public static void main(String[] args) {
        SpringApplication.from(ConsultorioMedicoApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
