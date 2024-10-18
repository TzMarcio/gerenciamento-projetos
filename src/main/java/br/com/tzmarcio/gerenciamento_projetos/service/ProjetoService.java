package br.com.tzmarcio.gerenciamento_projetos.service;

import br.com.tzmarcio.gerenciamento_projetos.enums.Status;
import br.com.tzmarcio.gerenciamento_projetos.model.Projeto;
import br.com.tzmarcio.gerenciamento_projetos.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository repository;

    public Projeto salvar(final Projeto projeto) {
        return this.repository.save(projeto);
    }

    public void deletar(final Long id) throws Exception {
        Projeto projeto = this.repository.findById(id).orElseThrow(() -> new Exception("Projeto não encontrado"));

        if(projeto.getStatus() == Status.INICIADO || projeto.getStatus() == Status.EM_ANDAMENTO || projeto.getStatus() == Status.ENCERRADO) {
            throw new Exception("Não é possível excluir um projeto iniciado ou encerrado.");
        }

        this.repository.delete(projeto);
    }

}
