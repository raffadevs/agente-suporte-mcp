import axios from 'axios'

export const sistemaChamadosApi = axios.create({
  baseURL: import.meta.env.VITE_SISTEMA_CHAMADOS_URL ?? 'http://localhost:8080',
})

export const agenteApi = axios.create({
  baseURL: import.meta.env.VITE_AGENTE_URL ?? 'http://localhost:8082',
})

export const mcpServerApi = axios.create({
  baseURL: import.meta.env.VITE_MCP_SERVER_URL ?? 'http://localhost:8081',
})
