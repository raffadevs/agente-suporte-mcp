import { mcpServerApi } from './api'
import type { SolicitacaoAprovacao } from '../types/api'

export async function listarAprovacoesPendentes(): Promise<SolicitacaoAprovacao[]> {
  const resposta = await mcpServerApi.get<SolicitacaoAprovacao[]>('/api/aprovacoes')
  return resposta.data
}

export async function decidirAprovacao(
  id: number,
  decisao: 'APROVADA' | 'REJEITADA',
  decididoPor: string,
): Promise<SolicitacaoAprovacao> {
  const resposta = await mcpServerApi.post<SolicitacaoAprovacao>(`/api/aprovacoes/${id}/decidir`, {
    decisao,
    decididoPor,
  })
  return resposta.data
}
