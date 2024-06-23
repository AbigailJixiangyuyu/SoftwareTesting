import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ComputerSales from '../components/ExerciseTesting/ComputerSales.vue';
import ChargeSystem from'../components/ExerciseTesting/ChargeSystem.vue';
import SalesSystem from'../components/ExerciseTesting/SalesSystem.vue';
import TriangleBoundary from'../components/ExerciseTesting/TriangleBoundary.vue';
import TriangleEquivalence from'../components/ExerciseTesting/TriangleEquivalence.vue';
import CalendarBoundary from'../components/ExerciseTesting/CalendarBoundary.vue';
import CalendarEquivalence from'../components/ExerciseTesting/CalendarEquivalence.vue';
import CalendarDecision from'../components/ExerciseTesting/CalendarDecision.vue';

import UnitTesting from'../components/TangxiaozhiTesting/UnitTesting.vue';
import IntegrationTesting from'../components/TangxiaozhiTesting/IntegrationTesting.vue';
import SystemTesting from'../components/TangxiaozhiTesting/SystemTesting.vue';

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
      {
        //单元测试
        path: 'tangxiaozhi_testing/unit_testing',
        name: 'Tangxiaozhi_unit_testing',
        component: UnitTesting
      },
      {
        //集成测试
        path: 'tangxiaozhi_testing/integration_testing',
        name: 'Tangxiaozhi_integration_testing',
        component: IntegrationTesting
      },
      {
        //系统测试
        path: 'tangxiaozhi_testing/system_testing',
        name: 'Tangxiaozhi_system_testing',
        component: SystemTesting
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
