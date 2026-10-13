package dev.raffadevs.mcpserver.aprovacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "solicitacao_aprovacao")
public class SolicitacaoAprovacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ferramenta;

    @Column(name = "chamado_id")
    private Long chamadoId;

    @Column(name = "acao_proposta", nullable = false)
    private String acaoProposta;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private String parametros;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAprovacao status;

    @Column(nullable = false)
    private boolean usada;

    @Column(name = "decidido_por")
    private String decididoPor;

    @Column(name = "decidido_em")
    private Instant decididoEm;

    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    protected SolicitacaoAprovacao() {
    }

    public SolicitacaoAprovacao(String ferramenta, Long chamadoId, String acaoProposta, String parametros) {
        this.ferramenta = ferramenta;
        this.chamadoId = chamadoId;
        this.acaoProposta = acaoProposta;
        this.parametros = parametros;
        this.status = StatusAprovacao.PENDENTE;
        this.usada = false;
        this.criadoEm = Instant.now();
    }

    public void decidir(StatusAprovacao decisao, String decididoPor) {
        this.status = decisao;
        this.decididoPor = decididoPor;
        this.decididoEm = Instant.now();
    }

    public void marcarUsada() {
        this.usada = true;
    }

    public boolean podeSerExecutada() {
        return status == StatusAprovacao.APROVADA && !usada;
    }

    public Long getId() {
        return id;
    }

    public String getFerramenta() {
        return ferramenta;
    }

    public Long getChamadoId() {
        return chamadoId;
    }

    public String getAcaoProposta() {
        return acaoProposta;
    }

    public String getParametros() {
        return parametros;
    }

    public StatusAprovacao getStatus() {
        return status;
    }

    public boolean isUsada() {
        return usada;
    }

    public String getDecididoPor() {
        return decididoPor;
    }

    public Instant getDecididoEm() {
        return decididoEm;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }
}
