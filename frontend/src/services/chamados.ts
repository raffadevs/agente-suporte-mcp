import { sistemaChamadosApi } from './api'
import type { Chamado, NovoChamado } from '../types/api'

export async function listarChamados(): Promise<Chamado[]> {
  const resposta = await sistemaChamadosApi.get<Chamado[]>('/api/chamados')
  return resposta.data
}

export async function buscarChamado(id: number): Promise<Chamado> {
  const resposta = await sistemaChamadosApi.get<Chamado>(`/api/chamados/${id}`)
  return resposta.data
}

export async function abrirChamado(novoChamado: NovoChamado): Promise<Chamado> {
  const resposta = await sistemaChamadosApi.post<Chamado>('/api/chamados', novoChamado)
  return resposta.data
}
