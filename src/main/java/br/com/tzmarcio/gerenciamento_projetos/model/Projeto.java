package br.com.tzmarcio.gerenciamento_projetos.model;

import br.com.tzmarcio.gerenciamento_projetos.enums.Risco;
import br.com.tzmarcio.gerenciamento_projetos.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
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

    @Transient
    private Long gerenteId;

    @Transient
    private String gerenteNome;

    @Transient
    private List<Long> pessoas;

    @Transient
    private List<Pessoa> membros;

    public Long getGerenteId() {
        return Objects.nonNull(gerente) ? gerente.getId() : gerenteId;
    }

    public String getGerenteNome() {
        return Objects.nonNull(gerente) ? gerente.getNome() : gerenteNome;
    }

    public void setGerente(Pessoa gerente) {
        this.gerente = gerente;
        if (Objects.nonNull(gerente)) {
            gerenteId = gerente.getId();
            gerenteNome = gerente.getNome();
        }
    }

    public List<Long> getPessoas() {
        return Objects.nonNull(pessoas) ? pessoas : new ArrayList<>();
    }

    public List<Pessoa> getMembros() {
        return Objects.nonNull(membros) ? membros : new ArrayList<>();
    }
}
