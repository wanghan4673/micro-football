import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/signin',
    name: 'signin',
    component: () => import('../views/SigninView.vue')
  },
  {
    path: '/signup',
    name: 'signup',
    component: () => import('../views/SignupView.vue')
  },
  {
    path: '/forum',
    name: 'forum',
    component: () => import('../views/ForumView.vue')
  },
  {
    path: '/profile',
    name: 'profile',
    component: () => import('../views/ProfileView.vue')
  },
  {
    path: '/Games',
    name: 'Games',
    component: () => import('../views/GamesView.vue')
  },
  {
    path: '/News',
    name: 'News',
    component: () => import('../views/NewsView.vue')
  },
  {
    path: '/Players',
    name: 'Players',
    component: () => import('../views/PlayersView.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router