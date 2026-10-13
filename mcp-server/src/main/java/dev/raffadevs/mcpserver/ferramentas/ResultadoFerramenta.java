package dev.raffadevs.mcpserver.ferramentas;

/**
 * Resultado de uma ferramenta de escrita. O modelo recebe este objeto serializado como
 * resposta da chamada de ferramenta — é assim que ele "sabe" que precisa esperar aprovação,
 * sem que isso dependa de nenhuma instrução de prompt ser seguida corretamente.
 */
public record ResultadoFerramenta(boolean sucesso, boolean aguardandoAprovacao, String aprovacaoId, String mensagem, Object dados) {

    public static ResultadoFerramenta aguardandoAprovacao(String aprovacaoId, String acaoProposta) {
        return new ResultadoFerramenta(false, true, aprovacaoId,
                "Ação sujeita a aprovação humana. Solicitação registrada com aprovacaoId=%s: %s".formatted(aprovacaoId, acaoProposta),
                null);
    }

    public static ResultadoFerramenta falha(String mensagem) {
        return new ResultadoFerramenta(false, false, null, mensagem, null);
    }

    public static ResultadoFerramenta sucesso(Object dados) {
        return new ResultadoFerramenta(true, false, null, "Ação executada com sucesso.", dados);
    }
}
