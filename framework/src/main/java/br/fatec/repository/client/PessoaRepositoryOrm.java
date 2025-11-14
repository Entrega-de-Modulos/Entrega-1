package br.fatec.repository.client;


import br.fatec.repository.orm.PessoaOrm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepositoryOrm extends JpaRepository<PessoaOrm, Long> {

}
