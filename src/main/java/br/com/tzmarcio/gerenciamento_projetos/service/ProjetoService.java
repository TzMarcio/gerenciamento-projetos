package br.com.tzmarcio.gerenciamento_projetos.service;

import br.com.tzmarcio.gerenciamento_projetos.enums.Status;
import br.com.tzmarcio.gerenciamento_projetos.model.Membros;
import br.com.tzmarcio.gerenciamento_projetos.model.Pessoa;
import br.com.tzmarcio.gerenciamento_projetos.model.Projeto;
import br.com.tzmarcio.gerenciamento_projetos.repository.MembrosRepository;
import br.com.tzmarcio.gerenciamento_projetos.repository.ProjetoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.util.List;
import java.util.Objects;

@Service
public class ProjetoService {

    private final ProjetoRepository repository;

    private final MembrosRepository membrosRepository;

    private final PessoaService pessoaService;

    @Autowired
    ProjetoService(final ProjetoRepository repository, final PessoaService pessoaService, final MembrosRepository membrosRepository) {
        this.repository = repository;
        this.pessoaService = pessoaService;
        this.membrosRepository = membrosRepository;
    }

    @Transactional
    public Projeto salvar(final Projeto projeto) throws IllegalArgumentException {
        if(Objects.isNull(projeto.getGerenteId())) {
            throw new IllegalArgumentException("É obrigatório selecionar um gerente.");
        }
        final Pessoa gerente = this.pessoaService.buscarPorId(projeto.getGerenteId()).orElseThrow(() -> new IllegalArgumentException("Gerente não encontrado"));
        projeto.setGerente(gerente);

        final Projeto entidade = this.repository.save(projeto);

        this.membrosRepository.deleteAllByProjetoId(projeto.getId());

        if(!projeto.getPessoas().isEmpty() && Objects.nonNull(projeto.getId())) {
            final List<Pessoa> pessoas = this.pessoaService.buscarPorIds(projeto.getPessoas());
            final List<Membros> membros = pessoas.stream().map(pessoa -> {
                final Membros membro = new Membros();
                membro.setPessoa(pessoa);
                membro.setProjeto(entidade);
                return membro;
            }).toList();
            this.membrosRepository.saveAll(membros);
        }

        return entidade;
    }

    public void deletar(final Long id) throws Exception {
        final Projeto projeto = this.repository.findById(id).orElseThrow(() -> new Exception("Projeto não encontrado"));

        if(projeto.getStatus() == Status.INICIADO || projeto.getStatus() == Status.EM_ANDAMENTO || projeto.getStatus() == Status.ENCERRADO) {
            throw new NoPermissionException("Não é possível excluir um projeto iniciado ou encerrado.");
        }

        this.repository.delete(projeto);
    }

    public List<Projeto> listar() {
        return this.repository.findAll();
    }

    public Projeto buscarPorId(final Long id) {
        final Projeto entidade = this.repository.findById(id).orElse(null);
        if(Objects.nonNull(entidade)) {
            final List<Membros> membros = this.membrosRepository.findByProjetoId(entidade.getId());
            entidade.setMembros(membros.stream().map(Membros::getPessoa).toList());
        }
        return entidade;
    }

    public void alterar(final Projeto projeto) {
        this.salvar(projeto);
    }

}
