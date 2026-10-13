package dev.raffadevs.agente.execucao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "passo_agente")
public class PassoAgente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "execucao_id", nullable = false)
    private Long execucaoId;

    @Column(nullable = false)
    private int ordem;

    @Column(nullable = false)
    private String ferramenta;

    @Column(nullable = false)
    private String entrada;

    @Column(nullable = false)
    private String saida;

    private String raciocinio;

    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    protected PassoAgente() {
    }

    public PassoAgente(Long execucaoId, int ordem, String ferramenta, String entrada, String saida) {
        this.execucaoId = execucaoId;
        this.ordem = ordem;
        this.ferramenta = ferramenta;
        this.entrada = entrada;
        this.saida = saida;
        this.criadoEm = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Long getExecucaoId() {
        return execucaoId;
    }

    public int getOrdem() {
        return ordem;
    }

    public String getFerramenta() {
        return ferramenta;
    }

    public String getEntrada() {
        return entrada;
    }

    public String getSaida() {
        return saida;
    }

    public String getRaciocinio() {
        return raciocinio;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }
}
