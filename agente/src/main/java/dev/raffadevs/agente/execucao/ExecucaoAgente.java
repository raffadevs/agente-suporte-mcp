package dev.raffadevs.agente.execucao;

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
@Table(name = "execucao_agente")
public class ExecucaoAgente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chamado_id", nullable = false)
    private Long chamadoId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusExecucao status;

    private String resultado;

    @Column(name = "iniciada_em", nullable = false)
    private Instant iniciadaEm;

    @Column(name = "finalizada_em")
    private Instant finalizadaEm;

    protected ExecucaoAgente() {
    }

    public ExecucaoAgente(Long chamadoId) {
        this.chamadoId = chamadoId;
        this.status = StatusExecucao.EM_ANDAMENTO;
        this.iniciadaEm = Instant.now();
    }

    public void concluir(String resultado) {
        this.status = StatusExecucao.CONCLUIDA;
        this.resultado = resultado;
        this.finalizadaEm = Instant.now();
    }

    public void falhar(String motivo) {
        this.status = StatusExecucao.FALHOU;
        this.resultado = motivo;
        this.finalizadaEm = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Long getChamadoId() {
        return chamadoId;
    }

    public StatusExecucao getStatus() {
        return status;
    }

    public String getResultado() {
        return resultado;
    }

    public Instant getIniciadaEm() {
        return iniciadaEm;
    }

    public Instant getFinalizadaEm() {
        return finalizadaEm;
    }
}
