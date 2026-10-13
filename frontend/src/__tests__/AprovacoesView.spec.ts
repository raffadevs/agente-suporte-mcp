import { describe, expect, it, vi } from 'vitest'
import { mount } from '@vue/test-utils'
import AprovacoesView from '../views/AprovacoesView.vue'
import * as aprovacoesService from '../services/aprovacoes'
import type { SolicitacaoAprovacao } from '../types/api'

vi.mock('../services/aprovacoes')

function flushPromises() {
  return new Promise((resolve) => setTimeout(resolve, 0))
}

function solicitacao(overrides: Partial<SolicitacaoAprovacao> = {}): SolicitacaoAprovacao {
  return {
    id: 1,
    ferramenta: 'desbloquearConta',
    chamadoId: 5,
    acaoProposta: 'Desbloquear a conta do usuário 9.',
    parametros: '{"usuarioId":9}',
    status: 'PENDENTE',
    usada: false,
    decididoPor: null,
    decididoEm: null,
    criadoEm: new Date().toISOString(),
    ...overrides,
  }
}

describe('AprovacoesView', () => {
  it('lista as aprovações pendentes ao montar', async () => {
    vi.mocked(aprovacoesService.listarAprovacoesPendentes).mockResolvedValue([solicitacao()])

    const wrapper = mount(AprovacoesView)
    await flushPromises()

    expect(wrapper.text()).toContain('Desbloquear a conta do usuário 9.')
  })

  it('remove a solicitação da lista ao aprovar', async () => {
    vi.mocked(aprovacoesService.listarAprovacoesPendentes).mockResolvedValue([solicitacao()])
    vi.mocked(aprovacoesService.decidirAprovacao).mockResolvedValue(solicitacao({ status: 'APROVADA' }))

    const wrapper = mount(AprovacoesView)
    await flushPromises()

    await wrapper.find('button.bg-emerald-600').trigger('click')
    await flushPromises()

    expect(aprovacoesService.decidirAprovacao).toHaveBeenCalledWith(1, 'APROVADA', 'analista@empresa.com')
    expect(wrapper.text()).toContain('Nenhuma aprovação pendente')
  })

  it('mostra mensagem de lista vazia quando não há pendências', async () => {
    vi.mocked(aprovacoesService.listarAprovacoesPendentes).mockResolvedValue([])

    const wrapper = mount(AprovacoesView)
    await flushPromises()

    expect(wrapper.text()).toContain('Nenhuma aprovação pendente')
  })
})
