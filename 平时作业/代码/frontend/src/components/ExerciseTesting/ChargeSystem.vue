<template>
    <div class="charge_css">
      <p class="question">
        问题简介: 每月的电话总费用=基本月租费+折扣后的实际的通话费，如果没有折扣则按实际通话费计算，基本月租费为25元，每分钟通话费为0.15元。
        实际通话费是否有折扣与当月的通话时间（分钟）和本年度至本月的累计未按时缴费的次数有关。跨年度未交费与折扣无关，但跨年未交部分每月需要交付总额5%的滞纳金。
        当月的通话分钟数和折扣比例及本年度未按时缴费次数之间有直接的对应关系，如果本年度的未按时缴费的次数超过本月通话时间所对应的容许值则免于折扣，并按实际的通话费计算。
      </p>
      <div  style="display: flex; align-items: center;">
        <!-- 文件上传组件 -->
        <el-upload
          action="http://47.116.193.81:25690/test/charge"
          multiple
          
          :on-success="handleSuccess"
          :on-remove="handleRemove"
          :file-list="fileList"
          :limit="10"
          :before-upload="beforeUpload"
          accept=".xlsx, .xls"
          :data="uploadData"
        >
          <el-button type="success">上传测试用例</el-button>
          <div class="el-upload__tip">只能上传excel文件</div>
        </el-upload>
        <el-button v-if="fileUrl" @click="handleFileClick">Download</el-button>
        <div  v-show="showChart" id="pieChart" style="width: 400px; height: 200px; margin-left: 20px;"></div>
      </div>
      
      <!-- 表格组件 -->
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="用例编号" width="100"></el-table-column>
        <el-table-column prop="minute" label="通话时长" width="100"></el-table-column>
        <el-table-column prop="times" label="不按时缴费次数" width="130"></el-table-column>
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
  import * as echarts from 'echarts'
  interface TableData {
    id: string;
    minute:number;
    times:number;
    expect: number;
    real: number;
    result: string;
    comment: string;
  }
  
  const tableData = ref<TableData[]>([]);
  const fileList = ref<any[]>([]);
  const fileUrl = ref<string | null>(null);
    const successRate = ref<number>(0);  
  const showChart = ref<boolean>(false);
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
      successRate.value = response.rate;
      showChart.value = tableData.value.length > 0;
      updateChart();
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
    showChart.value = false;
  };
  const updateChart = () => {
    const chartDom = document.getElementById('pieChart')!;
    const myChart = echarts.init(chartDom);
    const failureRate = 1 - successRate.value;
    const option = {
      title: {
        text: '测试用例结果',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: '测试结果',
          type: 'pie',
          radius: '50%',
          data: [
            { value: successRate.value, name: '通过', itemStyle: { color: 'green' }  },
            { value: failureRate, name: '失败', itemStyle: { color: 'red' } }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
    option && myChart.setOption(option);
  };
  </script>
  
  <style scoped>
  .charge_css {
    width: 70%;
  }
  </style>
  