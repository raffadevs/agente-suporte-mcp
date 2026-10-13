CREATE TABLE solicitacao_aprovacao (
    id BIGSERIAL PRIMARY KEY,
    ferramenta VARCHAR(100) NOT NULL,
    chamado_id BIGINT,
    acao_proposta TEXT NOT NULL,
    parametros JSONB NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDENTE', 'APROVADA', 'REJEITADA')),
    usada BOOLEAN NOT NULL DEFAULT FALSE,
    decidido_por VARCHAR(150),
    decidido_em TIMESTAMPTZ,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_solicitacao_aprovacao_status ON solicitacao_aprovacao (status);
