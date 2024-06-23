<template>
    <div class="testing">
      <p class="question">
        系统测试说明：系统测试的主要目的是验证整个糖尿病饮食管理与营养评估系统是否满足设计要求，并在实际使用环境中能够稳定、可靠地运行。以类为单元，采用独立的单元测试策略，通过设计相应的驱动来测试类中的方法。再选择类的被测方法中，根据方法的规模和复杂度进行判定。非空非注释代码代码行LOC>20，或者复杂度VG>3的方法进行单元测试，其他方法不进行单元测试。
      </p>
      <div style="display: flex; align-items: center;">
        <!-- 开始测试组件 -->
        
        <el-button type="success">开始系统测试</el-button>
         
      </div>
      
      
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref } from 'vue';
  import { ElUpload, ElButton, ElTable, ElTableColumn, ElMessage } from 'element-plus';
  import 'element-plus/dist/index.css';
  import axios from 'axios';
  
  
  </script>
  
  <style scoped>
  .testing {
    width: 70%;
  }
  </style>
  