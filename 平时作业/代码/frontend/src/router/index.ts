import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ComputerSales from '../components/ComputerSales.vue';
import ChargeSystem from'../components/ChargeSystem.vue';
import SalesSystem from'../components/SalesSystem.vue';
const routes: Array<RouteRecordRaw> = [
  {
    path: '/home',
    name: 'home',
    component: HomeView,
    children: [
      {
        path: 'computer_sales',
        name: 'Computer_sales',
        component: ComputerSales
      },
      {
        path: 'charge_system',
        name: 'Charge_system',
        component: ChargeSystem
      },
      {
        path: 'sales_system',
        name: 'Sales_system',
        component: SalesSystem
      },
    ]
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
