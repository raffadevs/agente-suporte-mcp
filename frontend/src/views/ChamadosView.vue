<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { RouterLink } from 'vue-router'
import { abrirChamado, listarChamados } from '../services/chamados'
import type { Chamado } from '../types/api'
import StatusBadge from '../components/StatusBadge.vue'

const chamados = ref<Chamado[]>([])
const carregando = ref(true)
const enviando = ref(false)
const erro = ref('')

const form = reactive({ solicitanteId: 1, titulo: '', descricao: '', categoria: 'acesso' })

async function carregar() {
  carregando.value = true
  chamados.value = await listarChamados()
  carregando.value = false
}

async function enviar() {
  erro.value = ''
  enviando.value = true
  try {
    const chamado = await abrirChamado({ ...form })
    chamados.value.unshift(chamado)
    form.titulo = ''
    form.descricao = ''
  } catch {
    erro.value = 'Não foi possível abrir o chamado.'
  } finally {
    enviando.value = false
  }
}

onMounted(carregar)
</script>

<template>
  <div class="max-w-2xl mx-auto px-5 py-8">
    <h2 class="text-lg font-bold text-slate-900">Chamados</h2>

    <form class="mt-4 bg-white border border-slate-200 rounded-lg p-4 flex flex-col gap-3" @submit.prevent="enviar">
      <div class="grid grid-cols-2 gap-3">
        <input v-model.number="form.solicitanteId" type="number" placeholder="Id do solicitante" required
               class="border border-slate-300 rounded-md px-3 py-2 text-sm" />
        <select v-model="form.categoria" class="border border-slate-300 rounded-md px-3 py-2 text-sm">
          <option value="acesso">acesso</option>
          <option value="infraestrutura">infraestrutura</option>
          <option value="duvida">dúvida</option>
        </select>
      </div>
      <input v-model="form.titulo" type="text" placeholder="Título" required
             class="border border-slate-300 rounded-md px-3 py-2 text-sm" />
      <textarea v-model="form.descricao" placeholder="Descreva o problema" required rows="3"
                class="border border-slate-300 rounded-md px-3 py-2 text-sm"></textarea>
      <button type="submit" :disabled="enviando"
              class="self-start bg-indigo-600 hover:bg-indigo-700 disabled:opacity-60 text-white font-semibold rounded-md px-4 py-2 text-sm">
        {{ enviando ? 'Enviando...' : 'Abrir chamado' }}
      </button>
      <p v-if="erro" class="text-sm text-red-600">{{ erro }}</p>
    </form>

    <div v-if="carregando" class="text-sm text-slate-500 mt-6">Carregando...</div>
    <ul v-else class="mt-6 flex flex-col gap-2">
      <li v-for="chamado in chamados" :key="chamado.id">
        <RouterLink :to="`/chamados/${chamado.id}`"
                    class="block bg-white border border-slate-200 rounded-lg p-3 hover:border-indigo-400">
          <div class="flex items-center justify-between">
            <span class="font-medium text-slate-900">#{{ chamado.id }} {{ chamado.titulo }}</span>
            <StatusBadge :status="chamado.status" />
          </div>
          <p class="text-xs text-slate-500 mt-1">{{ chamado.categoria }} · prioridade {{ chamado.prioridade }}</p>
        </RouterLink>
      </li>
      <li v-if="chamados.length === 0" class="text-sm text-slate-500">Nenhum chamado aberto ainda.</li>
    </ul>
  </div>
</template>
