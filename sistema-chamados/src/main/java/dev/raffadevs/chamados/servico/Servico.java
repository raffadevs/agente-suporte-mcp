package dev.raffadevs.chamados.servico;

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
@Table(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusServico status;

    @Column(name = "atualizado_em", nullable = false)
    private Instant atualizadoEm;

    protected Servico() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public StatusServico getStatus() {
        return status;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }
}
