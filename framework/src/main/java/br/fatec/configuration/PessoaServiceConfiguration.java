package br.fatec.configuration;

import br.fatec.repository.PessoaRepository;
import br.fatec.services.PessoaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PessoaServiceConfiguration {
    @Bean
    public PessoaService pessoaService(PessoaRepository pessoaRepository) {
        return new PessoaService(pessoaRepository);
    }
}
