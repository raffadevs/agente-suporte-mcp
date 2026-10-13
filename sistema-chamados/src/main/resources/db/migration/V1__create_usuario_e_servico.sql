CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    conta_bloqueada BOOLEAN NOT NULL DEFAULT FALSE,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE servico (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL CHECK (status IN ('OPERACIONAL', 'INSTAVEL', 'FORA')),
    atualizado_em TIMESTAMPTZ NOT NULL DEFAULT now()
);

INSERT INTO usuario (nome, email, conta_bloqueada) VALUES
    ('Ana Souza', 'ana.souza@empresa.com', TRUE),
    ('Bruno Lima', 'bruno.lima@empresa.com', FALSE),
    ('Carla Dias', 'carla.dias@empresa.com', FALSE);

INSERT INTO servico (nome, status) VALUES
    ('sistema-ponto', 'INSTAVEL'),
    ('vpn', 'OPERACIONAL'),
    ('email-corporativo', 'OPERACIONAL');
