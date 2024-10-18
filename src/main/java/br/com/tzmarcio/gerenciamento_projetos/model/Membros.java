package br.com.tzmarcio.gerenciamento_projetos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "membros")
public class Membros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idprojeto", nullable = false)
    private Projeto projeto;

    @ManyToOne
    @JoinColumn(name = "idpessoa", nullable = false)
    private Pessoa pessoa;

}
