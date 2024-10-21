package br.com.tzmarcio.gerenciamento_projetos.service;

import br.com.tzmarcio.gerenciamento_projetos.model.Pessoa;
import br.com.tzmarcio.gerenciamento_projetos.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository repository;

    @Autowired
    PessoaService(final PessoaRepository repository) {
        this.repository = repository;
    }

    public List<Pessoa> buscarGerentes(final String nome) {
        return repository.findByNomeContainingIgnoreCaseAndGerenteIsTrue(nome);
    }

    public List<Pessoa> buscarFuncionarios(final String nome) {
        return repository.findByNomeContainingIgnoreCaseAndFuncionarioIsTrue(nome);
    }

    public Optional<Pessoa> buscarPorId(final Long id) {
        return repository.findById(id);
    }

    public List<Pessoa> buscarPorIds(final List<Long> ids) {
        return repository.findAllById(ids);
    }

    public Pessoa salvar(final Pessoa pessoa) {
        return repository.save(pessoa);
    }

}
