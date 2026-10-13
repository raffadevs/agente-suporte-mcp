package dev.raffadevs.chamados.chamado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "chamado")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "solicitante_id", nullable = false)
    private Long solicitanteId;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado status;

    private String resolucao;

    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private Instant atualizadoEm;

    protected Chamado() {
    }

    public Chamado(Long solicitanteId, String titulo, String descricao, String categoria, Prioridade prioridade) {
        this.solicitanteId = solicitanteId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.prioridade = prioridade;
        this.status = StatusChamado.ABERTO;
        this.criadoEm = Instant.now();
        this.atualizadoEm = this.criadoEm;
    }

    public void atualizarStatus(StatusChamado novoStatus, String resolucao) {
        this.status = novoStatus;
        if (resolucao != null && !resolucao.isBlank()) {
            this.resolucao = resolucao;
        }
        this.atualizadoEm = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Long getSolicitanteId() {
        return solicitanteId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public String getResolucao() {
        return resolucao;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }
}
