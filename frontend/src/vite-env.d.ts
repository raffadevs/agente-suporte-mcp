/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_SISTEMA_CHAMADOS_URL: string;
  readonly VITE_AGENTE_URL: string;
  readonly VITE_MCP_SERVER_URL: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
