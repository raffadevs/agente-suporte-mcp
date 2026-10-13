export type StatusChamado = 'ABERTO' | 'EM_ATENDIMENTO' | 'AGUARDANDO_APROVACAO' | 'RESOLVIDO' | 'FECHADO'

export interface Chamado {
  id: number
  solicitanteId: number
  titulo: string
  descricao: string
  categoria: string
  prioridade: 'BAIXA' | 'MEDIA' | 'ALTA'
  status: StatusChamado
  resolucao: string | null
  criadoEm: string
  atualizadoEm: string
}

export interface NovoChamado {
  solicitanteId: number
  titulo: string
  descricao: string
  categoria: string
  prioridade?: string
}

export interface PassoAgente {
  id: number
  ordem: number
  ferramenta: string
  entrada: string
  saida: string
  raciocinio: string | null
  criadoEm: string
}

export type StatusExecucao = 'EM_ANDAMENTO' | 'CONCLUIDA' | 'FALHOU'

export interface ExecucaoAgente {
  id: number
  chamadoId: number
  status: StatusExecucao
  resultado: string | null
  iniciadaEm: string
  finalizadaEm: string | null
  passos: PassoAgente[]
}

export type StatusAprovacao = 'PENDENTE' | 'APROVADA' | 'REJEITADA'

export interface SolicitacaoAprovacao {
  id: number
  ferramenta: string
  chamadoId: number | null
  acaoProposta: string
  parametros: string
  status: StatusAprovacao
  usada: boolean
  decididoPor: string | null
  decididoEm: string | null
  criadoEm: string
}
