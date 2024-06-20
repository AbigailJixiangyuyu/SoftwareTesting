import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ComputerSales from '../components/ComputerSales.vue';
import ChargeSystem from'../components/ChargeSystem.vue';
import SalesSystem from'../components/SalesSystem.vue';
import TriangleBoundary from'../components/TriangleBoundary.vue';
import TriangleEquivalence from'../components/TriangleEquivalence.vue';
import CalendarBoundary from'../components/CalendarBoundary.vue';
import CalendarEquivalence from'../components/CalendarEquivalence.vue';
import CalendarDecision from'../components/CalendarDecision.vue';
const routes: Array<RouteRecordRaw> = [
  {
    path: '/home',
    name: 'home',
    component: HomeView,
    children: [
      {
        //电脑销售
        path: 'computer_sales',
        name: 'Computer_sales',
        component: ComputerSales
      },
      {
        //收费
        path: 'charge_system',
        name: 'Charge_system',
        component: ChargeSystem
      },
      {
        //销售
        path: 'sales_system',
        name: 'Sales_system',
        component: SalesSystem
      },
      {
        //三角形边界值
        path: 'triangle_system/boundary',
        name: 'Triangle_system_boundary',
        component: TriangleBoundary
      },
      {
        //三角形等价类
        path: 'triangle_system/equivalence',
        name: 'Triangle_system_equivalence',
        component: TriangleEquivalence
      },
      {
        //万年历等价类
        path: 'calendar_system/equivalence',
        name: 'Calendar_system_equivalence',
        component: CalendarEquivalence
      },
      {
        //万年历边界值
        path: 'calendar_system/boundary',
        name: 'Calendar_system_boundary',
        component: CalendarBoundary
      },
      {
        //万年历决策表
        path: 'calendar_system/decision',
        name: 'Calendar_system_decision',
        component: CalendarDecision
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
