<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { buscarChamado } from '../services/chamados'
import { iniciarAgente, listarExecucoes } from '../services/execucoes'
import type { Chamado, ExecucaoAgente } from '../types/api'
import StatusBadge from '../components/StatusBadge.vue'
import PassoAgenteItem from '../components/PassoAgenteItem.vue'

const route = useRoute()
const chamadoId = Number(route.params.id)

const chamado = ref<Chamado | null>(null)
const execucoes = ref<ExecucaoAgente[]>([])
const processando = ref(false)
const erro = ref('')

async function carregar() {
  chamado.value = await buscarChamado(chamadoId)
  execucoes.value = await listarExecucoes(chamadoId)
}

async function processarComAgente() {
  erro.value = ''
  processando.value = true
  try {
    await iniciarAgente(chamadoId)
    await carregar()
  } catch {
    erro.value = 'Não foi possível processar o chamado com o agente.'
  } finally {
    processando.value = false
  }
}

onMounted(carregar)
</script>

<template>
  <div class="max-w-2xl mx-auto px-5 py-8" v-if="chamado">
    <div class="flex items-center justify-between">
      <h2 class="text-lg font-bold text-slate-900">#{{ chamado.id }} {{ chamado.titulo }}</h2>
      <StatusBadge :status="chamado.status" />
    </div>
    <p class="text-sm text-slate-600 mt-2">{{ chamado.descricao }}</p>
    <p class="text-xs text-slate-500 mt-1">categoria: {{ chamado.categoria }} · prioridade: {{ chamado.prioridade }}</p>
    <p v-if="chamado.resolucao" class="text-sm text-emerald-700 mt-3 bg-emerald-50 border border-emerald-200 rounded-md p-3">
      Resolução: {{ chamado.resolucao }}
    </p>

    <button @click="processarComAgente" :disabled="processando"
            class="mt-4 bg-indigo-600 hover:bg-indigo-700 disabled:opacity-60 text-white font-semibold rounded-md px-4 py-2 text-sm">
      {{ processando ? 'Processando...' : 'Processar com o agente' }}
    </button>
    <p v-if="erro" class="text-sm text-red-600 mt-2">{{ erro }}</p>

    <h3 class="text-sm font-bold text-slate-900 mt-6">Execuções do agente</h3>
    <div v-if="execucoes.length === 0" class="text-sm text-slate-500 mt-2">Nenhuma execução ainda.</div>
    <div v-for="execucao in execucoes" :key="execucao.id" class="mt-3 border border-slate-200 rounded-lg p-3 bg-white">
      <div class="flex items-center justify-between">
        <span class="text-xs text-slate-500">{{ new Date(execucao.iniciadaEm).toLocaleString() }}</span>
        <StatusBadge :status="execucao.status" />
      </div>
      <p v-if="execucao.resultado" class="text-sm text-slate-700 mt-2">{{ execucao.resultado }}</p>
      <ul class="mt-2 flex flex-col gap-2">
        <PassoAgenteItem v-for="passo in execucao.passos" :key="passo.id" :passo="passo" />
      </ul>
    </div>
  </div>
</template>
