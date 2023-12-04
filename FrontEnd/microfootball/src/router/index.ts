import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import { topNavStore } from '@/stores/topNav'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/signin',
    name: 'signin',
    component: () => import('../views/UserViews/SignIn.vue')
  },
  {
    path: '/forum',
    name: 'forum',
    component: () => import('../views/ForumViews/ForumView.vue')
  },
  {
    path: '/Games',
    name: 'Games',
    component: () => import('../views/GamesViews/GamesView.vue')
  },
  {
    path: '/News',
    name: 'News',
    component: () => import('../views/NewsViews/NewsView.vue')
  },
  {
    path: '/Players',
    name: 'Players',
    component: () => import('../views/PlayersViews/PlayersView.vue')
  },
  {
    path: '/IM',
    name: 'IM',
    component: () => import('../views/IMViews/IMView.vue')
  },
  {
    path: '/forum/post',
    name: '/forum/post',
    component: () => import('../views/ForumViews/ForumPost.vue')
  },
  {
    path: '/forum/newpost',
    name: 'post',
    component: () => import('../views/ForumViews/ForumNewPost.vue')
  },
  {
    path: '/adminHome',
    name: 'adminHome',
    component: () => import('../views/AdminViews/AdminHomeView.vue')
  },
  {
    path: '/userManage',
    name: 'userManage',
    component: () => import('../views/AdminViews/UserManageView.vue')
  },
  {
    path: '/forumManage',
    name: 'forumManage',
    component: () => import('../views/AdminViews/ForumManageView.vue')
  },
  {
    path: '/newsManage',
    name: 'newsManage',
    component: () => import('../views/AdminViews/NewsManageView.vue')
  },
  {
    path: '/announceManage',
    name: 'announceManage',
    component: () => import('../views/AdminViews/AnnounceManageView.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const topNav = topNavStore()
  const isAdmin = to.query.isAdmin
  if (to.name === 'signin') {
      topNav.bothNotShow()
  } else if (to.name === 'adminHome' || to.name === 'userManage' || to.name === 'forumManage' || to.name === 'newsManage' || to.name === 'announceManage'){
    topNav.showAdminNav()
  } else {
    topNav.showTopNav()
  }
  next()
})

export default router
