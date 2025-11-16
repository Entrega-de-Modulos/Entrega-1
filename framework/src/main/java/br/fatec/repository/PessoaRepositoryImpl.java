package br.fatec.repository;

import br.fatec.entidades.Pessoa;
import br.fatec.repository.adapters.PessoaAdapter;
import br.fatec.repository.client.PessoaRepositoryOrm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PessoaRepositoryImpl implements PessoaRepository {
    private final PessoaRepositoryOrm pessoaRepositoryOrm;
    private final PessoaAdapter pessoaAdapter;
    public PessoaRepositoryImpl(PessoaRepositoryOrm pessoaRepositoryOrm, PessoaAdapter pessoaAdapter) {
        this.pessoaAdapter = pessoaAdapter;
        this.pessoaRepositoryOrm = pessoaRepositoryOrm;
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) {
        var pessoaOrm = pessoaAdapter.toOrm(pessoa);
        var pessoaOrmSalva = pessoaRepositoryOrm.save(pessoaOrm);
        return pessoaAdapter.toEntidade(pessoaOrmSalva);
    }

    @Override
    public Pessoa obterPorId(Long id) {
        var pessoaOrm = pessoaRepositoryOrm.findById(id);
        return pessoaOrm.map(pessoaAdapter::toEntidade).orElse(null);
    }

    @Override
    public List<Pessoa> obterPaginado(int pagina, int tamanho) {
        var pageable = org.springframework.data.domain.PageRequest.of(pagina - 1, tamanho);
        var page = pessoaRepositoryOrm.findAll(pageable);
        return page.map(pessoaAdapter::toEntidade).getContent();
    }
}
