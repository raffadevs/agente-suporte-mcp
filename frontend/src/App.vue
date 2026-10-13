<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <div class="min-h-screen">
    <header class="bg-white border-b border-slate-200">
      <div class="max-w-4xl mx-auto px-5 py-4 flex items-center justify-between">
        <div>
          <h1 class="text-base font-bold text-slate-900">agente-suporte-mcp</h1>
          <p class="text-xs text-slate-500">Agente de IA com ferramentas via MCP e aprovação humana</p>
        </div>
        <nav class="flex gap-4 text-sm font-medium">
          <RouterLink to="/" class="text-slate-600 hover:text-indigo-600">Chamados</RouterLink>
          <RouterLink to="/aprovacoes" class="text-slate-600 hover:text-indigo-600">Aprovações</RouterLink>
        </nav>
      </div>
    </header>
    <main>
      <RouterView />
    </main>
  </div>
</template>
