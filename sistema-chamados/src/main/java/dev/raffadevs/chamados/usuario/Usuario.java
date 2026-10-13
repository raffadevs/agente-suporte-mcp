package dev.raffadevs.chamados.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "conta_bloqueada", nullable = false)
    private boolean contaBloqueada;

    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    protected Usuario() {
    }

    public void desbloquear() {
        this.contaBloqueada = false;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public boolean isContaBloqueada() {
        return contaBloqueada;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }
}
