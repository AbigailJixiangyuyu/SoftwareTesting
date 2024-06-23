<template>
    <div class="testing">
      <p class="question">
        单元测试说明：在本项目中，单元测试的执行策略基于类和方法的选择。首先，我们对每个类中的方法根据非空非注释代码行数（LOC）超过20或复杂度超过3的标准来选择需要进行单元测试的方法，确保关键且复杂的功能得到充分的验证。本单元测试计划作为整个软件开发项目的一部分，包含计划阶段、设计阶段、实现阶段和执行阶段四个阶段。该计划主要处理与糖尿病饮食管理与营养评估系统单元测试有关的任务安排、资源需求、人力需求、风险管理、进度安排等内容。
      </p>
      <div style="display: flex; align-items: center;">
        <!--开始测试组件 -->
        
          <el-button type="success" @click="startUnitTest" :loading="loading">开始单元测试</el-button>
          
      </div>
      
      <el-button v-if="reportUrl" @click="viewReport">前往查看报告</el-button>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref } from 'vue';
  import { ElUpload, ElButton, ElTable, ElTableColumn, ElMessage } from 'element-plus';
  import 'element-plus/dist/index.css';
  import axios from 'axios';
  import { get } from "../../axios/axiosConfig.js";

  const loading = ref(false);
  const reportUrl = ref<string | null>(null);

  const startUnitTest = async () => {
    loading.value = true;
  try {
    const response = await get('/testurl/unit');
    console.log('Response data:', response.data);
    reportUrl.value = response.data;
    ElMessage.success('单元测试成功');
  } catch (error) {
    console.error('Error during unit test:', error);
    ElMessage.error('单元测试失败');
  }finally {
    loading.value = false;
  }
};
const viewReport = () => {
  if (reportUrl.value) {
    window.open(reportUrl.value, '_blank');
  } else {
    ElMessage.error('报告URL无效');
  }
};
  </script>
  
  <style scoped>
  .testing {
    width: 70%;
  }
  </style>
  