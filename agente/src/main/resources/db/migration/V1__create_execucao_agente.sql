CREATE TABLE execucao_agente (
    id BIGSERIAL PRIMARY KEY,
    chamado_id BIGINT NOT NULL,
    status VARCHAR(30) NOT NULL CHECK (status IN ('EM_ANDAMENTO', 'CONCLUIDA', 'FALHOU')),
    resultado TEXT,
    iniciada_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    finalizada_em TIMESTAMPTZ
);

CREATE TABLE passo_agente (
    id BIGSERIAL PRIMARY KEY,
    execucao_id BIGINT NOT NULL REFERENCES execucao_agente(id),
    ordem INT NOT NULL,
    ferramenta VARCHAR(100) NOT NULL,
    entrada TEXT NOT NULL,
    saida TEXT NOT NULL,
    raciocinio TEXT,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_passo_agente_execucao ON passo_agente (execucao_id);
