CREATE TABLE chamado (
    id BIGSERIAL PRIMARY KEY,
    solicitante_id BIGINT NOT NULL REFERENCES usuario(id),
    titulo VARCHAR(200) NOT NULL,
    descricao TEXT NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    prioridade VARCHAR(20) NOT NULL CHECK (prioridade IN ('BAIXA', 'MEDIA', 'ALTA')),
    status VARCHAR(30) NOT NULL CHECK (status IN ('ABERTO', 'EM_ATENDIMENTO', 'AGUARDANDO_APROVACAO', 'RESOLVIDO', 'FECHADO')),
    resolucao TEXT,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NOT NULL DEFAULT now()
);

INSERT INTO chamado (solicitante_id, titulo, descricao, categoria, prioridade, status) VALUES
    (1, 'Não consigo acessar o sistema de ponto', 'Desde ontem o sistema de ponto não aceita meu login, aparece erro de conexão.', 'acesso', 'ALTA', 'ABERTO');
