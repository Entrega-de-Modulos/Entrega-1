package br.fatec.repository;

import br.fatec.entidades.Pessoa;

import java.util.List;

public interface PessoaRepository {
    Pessoa salvar(Pessoa pessoa);
    Pessoa obterPorId(Long id);
    List<Pessoa> obterPaginado(int pagina, int tamanho);
}

