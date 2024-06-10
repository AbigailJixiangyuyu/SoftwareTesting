import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/home',
    name: 'home',
    component: HomeView
  },
  {
    path:"",
    redirect: "/home"
 },
  {
    //任意匹配
    path:"/:pathMatch(.*)",
    name:"notfound",
    component: () => import( /* webpackChunkName: 'NotFound' */ "../views/NotFound.vue"),
   },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
