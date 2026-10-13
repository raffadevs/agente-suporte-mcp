<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { decidirAprovacao, listarAprovacoesPendentes } from '../services/aprovacoes'
import type { SolicitacaoAprovacao } from '../types/api'

const pendentes = ref<SolicitacaoAprovacao[]>([])
const carregando = ref(true)
const decidindoId = ref<number | null>(null)
const nomeAnalista = ref('analista@empresa.com')

async function carregar() {
  carregando.value = true
  pendentes.value = await listarAprovacoesPendentes()
  carregando.value = false
}

async function decidir(id: number, decisao: 'APROVADA' | 'REJEITADA') {
  decidindoId.value = id
  try {
    await decidirAprovacao(id, decisao, nomeAnalista.value)
    pendentes.value = pendentes.value.filter((p) => p.id !== id)
  } finally {
    decidindoId.value = null
  }
}

onMounted(carregar)
</script>

<template>
  <div class="max-w-2xl mx-auto px-5 py-8">
    <h2 class="text-lg font-bold text-slate-900">Aprovações pendentes</h2>
    <p class="text-sm text-slate-500 mt-1">
      Ações de escrita propostas pelo agente ficam aqui até um analista aprovar ou rejeitar.
    </p>

    <input v-model="nomeAnalista" type="text" placeholder="Seu e-mail (analista)"
           class="mt-4 border border-slate-300 rounded-md px-3 py-2 text-sm w-full" />

    <div v-if="carregando" class="text-sm text-slate-500 mt-6">Carregando...</div>
    <ul v-else class="mt-4 flex flex-col gap-3">
      <li v-for="solicitacao in pendentes" :key="solicitacao.id"
          class="bg-white border border-slate-200 rounded-lg p-4">
        <p class="text-xs text-slate-400">#{{ solicitacao.id }} · chamado {{ solicitacao.chamadoId ?? '—' }} · {{ solicitacao.ferramenta }}</p>
        <p class="text-sm text-slate-900 mt-1">{{ solicitacao.acaoProposta }}</p>
        <div class="flex gap-2 mt-3">
          <button @click="decidir(solicitacao.id, 'APROVADA')" :disabled="decidindoId === solicitacao.id"
                  class="bg-emerald-600 hover:bg-emerald-700 disabled:opacity-60 text-white text-sm font-semibold rounded-md px-3 py-1.5">
            Aprovar
          </button>
          <button @click="decidir(solicitacao.id, 'REJEITADA')" :disabled="decidindoId === solicitacao.id"
                  class="bg-red-600 hover:bg-red-700 disabled:opacity-60 text-white text-sm font-semibold rounded-md px-3 py-1.5">
            Rejeitar
          </button>
        </div>
      </li>
      <li v-if="pendentes.length === 0" class="text-sm text-slate-500">Nenhuma aprovação pendente.</li>
    </ul>
  </div>
</template>
