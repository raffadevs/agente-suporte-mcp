import { createRouter, createWebHistory } from 'vue-router'
import ChamadosView from '../views/ChamadosView.vue'
import ChamadoDetalheView from '../views/ChamadoDetalheView.vue'
import AprovacoesView from '../views/AprovacoesView.vue'

export const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'chamados', component: ChamadosView },
    { path: '/chamados/:id', name: 'chamado-detalhe', component: ChamadoDetalheView },
    { path: '/aprovacoes', name: 'aprovacoes', component: AprovacoesView },
  ],
})
