-- O container sobe com um banco default (POSTGRES_DB). Este script cria os
-- outros dois, um por serviço, compartilhando uma única instância de Postgres
-- por economia de recursos (em produção, seriam instâncias separadas).
CREATE DATABASE mcp_server;
CREATE DATABASE agente;
