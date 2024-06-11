<template>
  <div class="container">
    <!-- 侧边栏，包括标题和导航栏 -->
    <div class="sidebar">
      <div class="sidebar-header">
        <h1>软件测试练习题</h1>
      </div>
      <!-- 侧边菜单 -->
      <el-menu
        :router="true"
        :default-active="$route.path" 
        class="el-menu-vertical-demo"
        background-color="rgb(207, 207, 207)"
        text-color="#333"
        active-text-color="#0000ff"
      >
        <!-- 一级导航1 -->
        <el-sub-menu index="1">
          <template #title>
            <span>判断三角形类型</span>
          </template>
          <!-- 二级导航1 -->
          <el-menu-item index="/home/triangleA">边界值法</el-menu-item>
          <el-menu-item index="/home/triangleB">等价类法</el-menu-item>
        </el-sub-menu>

        <!-- 一级导航2 -->
        <el-sub-menu index="2">
          <template #title>
            <span>万年历问题</span>
          </template>
          <!-- 二级导航2 -->
          <el-menu-item index="/home/calendarA">边界值法</el-menu-item>
          <el-menu-item index="/home/calendarB">等价类法、决策表法</el-menu-item>
        </el-sub-menu>

        <!-- 一级导航3 -->
        <el-sub-menu index="3">
          <template #title>
            <span>电脑销售系统</span>
          </template>
          <!-- 二级导航3 -->
          <el-menu-item index="/home/computer_sales" >边界值法</el-menu-item>
        </el-sub-menu>

        <!-- 一级导航4 -->
        <el-sub-menu index="4">
          <template #title>
            <span>电信收费问题</span>
          </template>
          <!-- 二级导航4 -->
          <el-menu-item index="/home/charge_system">边界值法</el-menu-item>
        </el-sub-menu>

        <!-- 一级导航5 -->
        <el-sub-menu index="5">
          <template #title>
            <span>销售系统问题</span>
          </template>
          <!-- 二级导航5 -->
          <el-menu-item index="/home/sales_system">白盒测试</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </div>

    <!-- 内容区 -->
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script lang="ts">

import { ElMenu, ElSubMenu, ElMenuItem } from 'element-plus';
import 'element-plus/dist/index.css';

export default{
  name: 'HomeView',
  data() {
        return {
        };
    },
  components: {
    ElMenu,
    ElSubMenu,
    ElMenuItem
  },
  methods: {
    /* handleSelect(key, keyPath) {
      if (key === '3-1') {
        this.$router.push('/home/computer_sales')
      }
    } */
  }
};
</script>

<style scoped>

.container {
  display: flex;
 
}

.sidebar {
  
  display: flex;
  flex-direction: column;
  background-color: rgb(207, 207, 207);
}

.sidebar-header {
  width: 220px;
  position:fixed;
  left:0;
  top:0;
  height:8%;
  background-color: rgb(207, 207, 207);
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
}

.sidebar-header h1 {
  margin: 0;
  font-size: 16px;
  color: #333;
}

.el-menu-vertical-demo {
  
  top: 8%;
  left: 0;
  width: 220px;
  min-height: 100%;
  position:fixed;
}
.content{
  margin-top:0px;
  margin-left: 220px; /* 左侧空出220px */
    padding: 20px; /* 可选：增加一些内边距，使内容不紧贴边框 */
}
</style>