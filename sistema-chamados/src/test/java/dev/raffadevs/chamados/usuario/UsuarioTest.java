package dev.raffadevs.chamados.usuario;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    void desbloquearLimpaFlagDeContaBloqueada() throws Exception {
        Usuario usuario = new Usuario();
        definirCampo(usuario, "contaBloqueada", true);

        usuario.desbloquear();

        assertThat(usuario.isContaBloqueada()).isFalse();
    }

    private void definirCampo(Object alvo, String nome, Object valor) throws Exception {
        Field campo = Usuario.class.getDeclaredField(nome);
        campo.setAccessible(true);
        campo.set(alvo, valor);
    }
}
