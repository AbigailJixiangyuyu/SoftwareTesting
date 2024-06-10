package com.tongji.service.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.tongji.model.dto.TimeRangeDTO;
import com.tongji.model.pojo.Glucose;
import com.tongji.model.vo.ResponseResult;
import com.tongji.service.FoodServiceApplication;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.hamcrest.collection.IsEmptyCollection;

import static org.hamcrest.CoreMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = FoodServiceApplication.class)
@Feature("血糖记录模块单元测试")
class GlucoseServiceImplTest {

    @Autowired
    GlucoseServiceImpl glucoseService;

    @Test
    @Story("用例名称：血糖信息查找")
    @DisplayName("测试时间范围为空的情况")
    @Description("返回时间范围不能为空的提示消息")
    void getGlucose1() {
        StpUtil.login(1);
        ResponseResult result =glucoseService.getGlucose(null);
        assertEquals("时间范围不能为空", result.getMessage());
    }

    @Test
    @Story("用例名称：血糖信息查找")
    @DisplayName("测试输入时间范围内无血糖记录的情况")
    @Description("返回空列表")
    void getGlucose2() {
        StpUtil.login(1);
        TimeRangeDTO timeRangeDTO=new TimeRangeDTO();
        timeRangeDTO.setStartTime(LocalDateTime.of(2019, 12, 20, 0, 0, 0));
        timeRangeDTO.setEndTime(LocalDateTime.of(2019, 12, 30, 0, 0, 0));
        ResponseResult responseResult =glucoseService.getGlucose(timeRangeDTO);
        List<Glucose> result = (List<Glucose>) responseResult.getData();
        List<Glucose> expected = List.of();
        assertEquals(expected, result);
    }

    @Test
    @Story("用例名称：血糖信息查找")
    @DisplayName("测试输入时间范围内有血糖记录的情况")
    @Description("返回正确的血糖值列表")
    void getGlucose3() {
        StpUtil.login(1);
        TimeRangeDTO timeRangeDTO=new TimeRangeDTO();
        timeRangeDTO.setStartTime(LocalDateTime.of(2021, 7, 30, 16, 0, 0));
        timeRangeDTO.setEndTime(LocalDateTime.of(2021, 7, 30, 17, 0, 0));
        ResponseResult responseResult =glucoseService.getGlucose(timeRangeDTO);
        List<Glucose> result = (List<Glucose>) responseResult.getData();
        Glucose glucose1 = new Glucose();
        glucose1.setId(2L);
        glucose1.setUserId(1L);
        glucose1.setGluValue(new BigDecimal("6.300"));
        glucose1.setTime(LocalDateTime.of(2021,7,30,16,43,0));
        Glucose glucose2 = new Glucose();
        glucose2.setId(3L);
        glucose2.setUserId(1L);
        glucose2.setGluValue(new BigDecimal("6.900"));
        glucose2.setTime(LocalDateTime.of(2021,7,30,16,58,0));
        List<Glucose> expected = List.of(glucose1,glucose2);
        assertEquals(expected.toString(), result.toString());
    }

    @Test
    @Story("用例名称：血糖信息删除")
    @DisplayName("删除不存在的血糖数据")
    @Description("应当输出血糖数据不存在的提示")
    void deleteGlucose1() {
        StpUtil.login(1);
        ResponseResult result = glucoseService.deleteGlucose(1L);
        assertEquals("该血糖数据不存在", result.getMessage());
    }

    @Test
    @Story("用例名称：血糖信息删除")
    @DisplayName("id为空")
    @Description("应当输出id不能为空的提示")
    void deleteGlucose2() {
        StpUtil.login(1);
        ResponseResult result = glucoseService.deleteGlucose(null);
        assertEquals("id不能为空", result.getMessage());
    }

    @Test
    void updateGlucose() {
    }

    @Test
    void addGlucose() {
    }
}