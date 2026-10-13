package dev.raffadevs.chamados.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.busca")
public class BuscaProperties {

    private int limiteArtigos = 4;
    private double similaridadeMinima = 0.5;

    public int getLimiteArtigos() {
        return limiteArtigos;
    }

    public void setLimiteArtigos(int limiteArtigos) {
        this.limiteArtigos = limiteArtigos;
    }

    public double getSimilaridadeMinima() {
        return similaridadeMinima;
    }

    public void setSimilaridadeMinima(double similaridadeMinima) {
        this.similaridadeMinima = similaridadeMinima;
    }
}
