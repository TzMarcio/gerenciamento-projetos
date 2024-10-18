package br.com.tzmarcio.gerenciamento_projetos.model;

import br.com.tzmarcio.gerenciamento_projetos.enums.Risco;
import br.com.tzmarcio.gerenciamento_projetos.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "projeto")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_previsao_fim")
    private LocalDate dataPrevisaoFim;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(length = 5000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(length = 45)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(length = 45)
    private Risco risco;

    @Column
    private Float orcamento;

    @ManyToOne
    @JoinColumn(name = "idgerente", nullable = false)
    private Pessoa gerente;

}
