package dev.raffadevs.chamados.artigo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "artigo")
public class Artigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String conteudo;

    @Column(nullable = false)
    private boolean indexado;

    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    protected Artigo() {
    }

    public void marcarIndexado() {
        this.indexado = true;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public boolean isIndexado() {
        return indexado;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }
}
