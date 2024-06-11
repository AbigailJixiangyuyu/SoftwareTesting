<template>
    <div class="computer_sales">
      <p class="question">
        问题简介: 一销售系统，如果销售员的年销售额大于200万RMB且请假天数不超过10天的情况下，现金到帐大于等于60%，则佣金（提成）系数为7，即佣金值为销售额除以佣金系数；现金到帐小于60%，佣金不予计算。所有其他情况且现金到帐小于等于85%，则按佣金系数均为6计算佣金，现金到账大于85%，佣金系数按5处理。
      </p>
      <div>
        <!-- 文件上传组件 -->
        <el-upload
          action="http://47.116.193.81:25690/test/sale_system"
          multiple
          
          :on-success="handleSuccess"
          :on-remove="handleRemove"
          :file-list="fileList"
          :limit="1"
          :before-upload="beforeUpload"
          accept=".xlsx, .xls"
          :data="uploadData"
        >
          <el-button type="success">上传测试用例</el-button>
          <div class="el-upload__tip">只能上传excel文件</div>
        </el-upload>
        <el-button v-if="fileUrl" @click="handleFileClick">Download</el-button>
      </div>
      
      <!-- 表格组件 -->
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="用例编号" width="100"></el-table-column>
        <el-table-column prop="sales" label="销售员年销售额" width="140"></el-table-column>
        <el-table-column prop="account" label="现金到账率" width="110"></el-table-column>
        <el-table-column prop="day" label="请假天数" width="100"></el-table-column>
        
        <el-table-column prop="expect" label="预期结果" width="120"></el-table-column>
        <el-table-column prop="real" label="实际结果" width="120"></el-table-column>
        <el-table-column prop="result" label="是否通过" width="120"></el-table-column>
        <el-table-column prop="comment" label="用例备注" width="200"></el-table-column>
      </el-table>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref } from 'vue';
  import { ElUpload, ElButton, ElTable, ElTableColumn, ElMessage } from 'element-plus';
  import 'element-plus/dist/index.css';
  import axios from 'axios';
  
  interface TableData {
    id: string;
    sales: number;
    account: number;
    day: number;
    expect: number;
    real: number;
    result: string;
    comment: string;
  }
  
  const tableData = ref<TableData[]>([]);
  const fileList = ref<any[]>([]);
  const fileUrl = ref<string | null>(null);
  const beforeUpload = (file: any) => {
    const isExcel = file.type === 'application/vnd.ms-excel' || file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
    if (!isExcel) {
      ElMessage.error('只能上传excel文件');
    }
    return isExcel;
  };
  
  const handleSuccess = (response: any, file: any) => {
    console.log('Response:', response);  // 添加调试日志
    if (response ) {
      tableData.value = response.result as TableData[];
      fileUrl.value=response.file;
    } else {
      ElMessage.error('文件处理失败');
    }
  };
  
  const handleFileClick = () => {
    if (fileUrl.value) {
      window.open(fileUrl.value, '_blank');
    } else {
      ElMessage.error('文件URL无效');
    }
  };
  const uploadData = (file: any) => {
    const formData = new FormData();
    formData.append('file', file.raw);
    return formData;
  };
  const handleRemove = () => {
    tableData.value = [];
    fileUrl.value = null;
    fileList.value =[];
  };
  </script>
  
  <style scoped>
  .computer_sales {
    width: 85%;
  }
  </style>
  