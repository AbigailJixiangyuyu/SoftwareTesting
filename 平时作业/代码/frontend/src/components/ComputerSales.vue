<template>
  <div class="computer_sales">
    <p class="question">
      问题简介: 电脑销售系统，主机（25￥单位价格，每月最多销售的数量为70），显示器（30￥单位价格，每月最多销售数量为80），外设（45￥单位价格，每月最多销售的数量为90）；每个销售员每月至少销售一台完整的机器，当系统的主机这个变量接受到-1值的时候，系统自动统计该销售员本月的销售总额。当销售额小于等于1000（包括1000）按照10%提佣金，当销售额在1000-1800之间（包括1800）的时候按照15%提佣金，当销售额大于1800时按照20%提佣金。（用边界值方法分析和设计测试用例）
    </p>
    <div>
      <!-- 文件上传组件 -->
      <el-upload
        action="http://47.116.193.81:25690/test/commission"
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
      <el-table-column prop="mainframes" label="主机销量" width="100"></el-table-column>
      <el-table-column prop="monitors" label="显示器销量" width="110"></el-table-column>
      <el-table-column prop="peripherals" label="外设销量" width="100"></el-table-column>
      <el-table-column prop="gross_sales" label="销售总额" width="100"></el-table-column>
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
  mainframes: number;
  monitors: number;
  peripherals: number;
  gross_sales:number;
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
