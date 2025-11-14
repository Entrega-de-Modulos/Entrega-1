package br.fatec.repository.adapters;

import br.fatec.entidades.Pessoa;
import br.fatec.repository.orm.PessoaOrm;
import org.springframework.stereotype.Component;

@Component
public class PessoaAdapter {
    public Pessoa toEntidade(PessoaOrm pessoaOrm) {
        return new Pessoa(
            pessoaOrm.getId(),
            pessoaOrm.getNome(),
            pessoaOrm.getDataNascimento(),
            pessoaOrm.isAtivo()
        );
    }

    public PessoaOrm toOrm(Pessoa pessoa) {
        return new PessoaOrm(
            pessoa.id(),
            pessoa.nome(),
            pessoa.dataNascimento(),
            pessoa.ativo()
        );
    }
}
