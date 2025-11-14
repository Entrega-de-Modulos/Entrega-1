package br.fatec.controllers;

import br.fatec.dtos.AtualizarPessoaDto;
import br.fatec.dtos.CriarPessoaDto;
import br.fatec.entidades.Pessoa;
import br.fatec.services.PessoaService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public List<Pessoa> listarTodos(
            @RequestParam(value = "pagina", defaultValue = "1") int pagina,
            @RequestParam(value = "tamanho", defaultValue = "10") int tamanho
    ) {
        return pessoaService.obterPaginado(pagina, tamanho);
    }

    @GetMapping("/{id}")
    public Pessoa obterPorId(@PathVariable("id") Long id) {
        return pessoaService.obterPorId(id);
    }

    @PostMapping
    public Pessoa criar(@RequestBody CriarPessoaDto criarPessoaDto) {
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var date = LocalDate.parse(criarPessoaDto.dataNascimento(), formatter);
        return pessoaService.criar(criarPessoaDto.nome(), date);
    }

    @PutMapping("/{id}")
    public Pessoa atualizar(
            @PathVariable Long id,
            @RequestBody AtualizarPessoaDto atualizarPessoaDto
    ) {
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var date = LocalDate.parse(atualizarPessoaDto.dataNascimento(), formatter);
        return pessoaService.atualizar(id, atualizarPessoaDto.nome(), date);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        pessoaService.excluir(id);
    }
}
