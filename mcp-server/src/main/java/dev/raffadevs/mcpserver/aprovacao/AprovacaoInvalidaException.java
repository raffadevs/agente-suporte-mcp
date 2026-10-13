package dev.raffadevs.mcpserver.aprovacao;

public class AprovacaoInvalidaException extends RuntimeException {
    public AprovacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
