package br.fatec.services;


import br.fatec.entidades.Pessoa;
import br.fatec.exception.DadosInvalidosException;
import br.fatec.exception.PessoaNaoEncontradaException;
import br.fatec.repository.PessoaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class PessoaService {
    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public Pessoa obterPorId(Long id) {
        var pessoa = repository.obterPorId(id);

        if (pessoa == null || !pessoa.ativo()) {
            throw new PessoaNaoEncontradaException("Pessoa não encontrada");
        }

        return pessoa;
    }

    public List<Pessoa> obterPaginado(int pagina, int tamanho) {
        return repository.obterPaginado(pagina, tamanho).stream()
                .filter(Pessoa::ativo)
                .toList();
    }

    public Pessoa criar(String nome, LocalDate dataNascimento) {
        var pessoa = new Pessoa(
                null,
                nome,
                dataNascimento,
                true
        );

        validarPessoa(pessoa);

        return repository.salvar(pessoa);
    }

    public Pessoa atualizar(Long id, String nome, LocalDate dataNascimento) {
        var pessoa = new Pessoa(
                id,
                nome,
                dataNascimento,
                true
        );

        validarPessoa(pessoa);

        return repository.salvar(pessoa);
    }

    public Pessoa excluir(Long id) {
        var pessoa = this.obterPorId(id);

        var pessoaDesativada = new Pessoa(
                pessoa.id(),
                pessoa.nome(),
                pessoa.dataNascimento(),
                false
        );

        return repository.salvar(pessoaDesativada);
    }


    private void validarPessoa(Pessoa pessoa) {
        if (pessoa.nome() == null || pessoa.nome().isBlank()) {
            throw new DadosInvalidosException("O nome da pessoa é inválido");
        }
        if (pessoa.dataNascimento() == null || pessoa.dataNascimento().isAfter(LocalDate.now())) {
            throw new DadosInvalidosException("A data de nascimento da pessoa é inválida");
        }
    }
}

