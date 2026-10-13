<script setup lang="ts">
defineProps<{ status: string }>()

const cores: Record<string, string> = {
  ABERTO: 'bg-slate-100 text-slate-700',
  EM_ATENDIMENTO: 'bg-blue-100 text-blue-700',
  AGUARDANDO_APROVACAO: 'bg-amber-100 text-amber-700',
  RESOLVIDO: 'bg-emerald-100 text-emerald-700',
  FECHADO: 'bg-slate-200 text-slate-500',
  PENDENTE: 'bg-amber-100 text-amber-700',
  APROVADA: 'bg-emerald-100 text-emerald-700',
  REJEITADA: 'bg-red-100 text-red-700',
  EM_ANDAMENTO: 'bg-blue-100 text-blue-700',
  CONCLUIDA: 'bg-emerald-100 text-emerald-700',
  FALHOU: 'bg-red-100 text-red-700',
}
</script>

<template>
  <span class="text-xs font-semibold px-2 py-0.5 rounded-full" :class="cores[status] ?? 'bg-slate-100 text-slate-700'">
    {{ status.replaceAll('_', ' ') }}
  </span>
</template>
