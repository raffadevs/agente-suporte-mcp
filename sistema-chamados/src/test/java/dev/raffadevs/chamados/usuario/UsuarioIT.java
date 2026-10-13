package dev.raffadevs.chamados.usuario;

import static org.assertj.core.api.Assertions.assertThat;

import dev.raffadevs.chamados.IntegrationTestBase;
import dev.raffadevs.chamados.usuario.dto.UsuarioResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

class UsuarioIT extends IntegrationTestBase {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void desbloqueiaContaDeUsuario() {
        UsuarioResponse antes = restTemplate.getForObject("/api/usuarios/1", UsuarioResponse.class);
        assertThat(antes.contaBloqueada()).isTrue();

        UsuarioResponse depois = restTemplate.postForObject("/api/usuarios/1/desbloquear", null, UsuarioResponse.class);

        assertThat(depois.contaBloqueada()).isFalse();
    }
}
