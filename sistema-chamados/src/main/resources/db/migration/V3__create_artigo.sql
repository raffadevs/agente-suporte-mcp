CREATE TABLE artigo (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    conteudo TEXT NOT NULL,
    indexado BOOLEAN NOT NULL DEFAULT FALSE,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now()
);

INSERT INTO artigo (titulo, conteudo) VALUES
    ('Como desbloquear minha conta',
     'Se sua conta foi bloqueada por tentativas de login incorretas, um analista de suporte pode desbloqueá-la após confirmar sua identidade. Abra um chamado na categoria acesso informando seu nome completo e e-mail corporativo.'),
    ('Sistema de ponto instável ou fora do ar',
     'Quando o sistema de ponto está instável, registre o horário manualmente em uma planilha de contingência e informe seu gestor. O suporte monitora o status do serviço e atualiza os usuários afetados quando ele volta ao normal.'),
    ('Como resetar a senha da VPN',
     'Para resetar a senha da VPN, acesse o portal de autoatendimento com seu e-mail corporativo. Caso o portal também esteja inacessível, abra um chamado na categoria acesso.'),
    ('Política de uso de e-mail corporativo',
     'O e-mail corporativo deve ser usado apenas para assuntos de trabalho. Encaminhar e-mails corporativos para contas pessoais é proibido pela política de segurança da empresa.'),
    ('Tempo médio de atendimento de chamados',
     'Chamados de prioridade alta são atendidos em até 4 horas úteis. Chamados de prioridade média em até 1 dia útil, e de prioridade baixa em até 3 dias úteis.'),
    ('Como solicitar acesso a um novo sistema',
     'Para solicitar acesso a um sistema que você ainda não utiliza, abra um chamado na categoria acesso informando o nome do sistema e a justificativa de uso, com aprovação do seu gestor.');
