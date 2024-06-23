<template>
    <div class="testing">
      <p class="question">
        集成测试说明：为了程序能够正确集成，针对程序进行集成测试任务，确保当单独开发的多个模块或组件组合在一起时，它们能够正确地协同工作。关注于模块间的接口，包括数据传递和函数调用的正确性，帮助发现单元测试中可能遗漏的问题，如接口不匹配或逻辑错误。本测试计划主要是针对软件的集成测试:不含硬件、系统测试以及单元测试（完成单元测试是前提）。 主要测试方法是黑盒测试方法。必要的集成测试是回归测试。
      </p>
      <div style="display: flex; align-items: center;">
        <!-- 开始测试组件 -->
        
          <el-button type="success" @click="startIntegrationTest"  :loading="loading">开始集成测试</el-button>
          
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

  const startIntegrationTest = async () => {
    loading.value = true;
  try {
    const response = await get('/testurl/int');
    console.log('Response data:', response.data);
    reportUrl.value = response.data;
    ElMessage.success('集成测试成功');
  } catch (error) {
    console.error('Error during integration test:', error);
    ElMessage.error('集成测试失败');
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
  