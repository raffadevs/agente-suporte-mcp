<script setup lang="ts">
import type { PassoAgente } from '../types/api'

defineProps<{ passo: PassoAgente }>()
</script>

<template>
  <li class="border border-slate-200 rounded-md p-3 bg-white">
    <div class="flex items-center justify-between">
      <span class="text-sm font-semibold text-slate-900">#{{ passo.ordem }} · {{ passo.ferramenta }}</span>
      <span class="text-xs text-slate-400">{{ new Date(passo.criadoEm).toLocaleString() }}</span>
    </div>
    <p class="text-xs text-slate-500 mt-1">entrada: <code class="break-all">{{ passo.entrada }}</code></p>
    <p class="text-xs text-slate-500 mt-1">saída: <code class="break-all">{{ passo.saida }}</code></p>
  </li>
</template>
