import { agenteApi } from './api'
import type { ExecucaoAgente } from '../types/api'

export async function iniciarAgente(chamadoId: number): Promise<ExecucaoAgente> {
  const resposta = await agenteApi.post<ExecucaoAgente>(`/api/chamados/${chamadoId}/iniciar`)
  return resposta.data
}

export async function listarExecucoes(chamadoId: number): Promise<ExecucaoAgente[]> {
  const resposta = await agenteApi.get<ExecucaoAgente[]>(`/api/chamados/${chamadoId}/execucoes`)
  return resposta.data
}
