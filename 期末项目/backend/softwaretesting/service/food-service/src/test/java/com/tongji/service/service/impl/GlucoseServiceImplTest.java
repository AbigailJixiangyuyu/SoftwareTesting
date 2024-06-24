package com.tongji.service.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.tongji.model.dto.GlucoseAddDTO;
import com.tongji.model.dto.GlucoseDTO;
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
    private GlucoseServiceImpl glucoseService;

    @Test
    @Story("血糖信息查找")
    @DisplayName("测试时间范围为空的情况")
    @Description("返回时间范围不能为空的提示消息")
    void getGlucose1() {
        StpUtil.login(1);
        TimeRangeDTO timeRangeDTO = new TimeRangeDTO();
        timeRangeDTO.setStartTime(null);
        timeRangeDTO.setEndTime(null);
        ResponseResult result =glucoseService.getGlucose(timeRangeDTO);
        assertEquals("时间范围不能为空", result.getMessage());
    }

    @Test
    @Story("血糖信息查找")
    @DisplayName("测试时间范围不合法的情况")
    @Description("返回时间范围不合法的提示消息")
    void getGlucose2() {
        StpUtil.login(1);
        TimeRangeDTO timeRangeDTO = new TimeRangeDTO();
        timeRangeDTO.setEndTime(LocalDateTime.of(2021, 7, 30, 16, 0, 0));
        timeRangeDTO.setStartTime(LocalDateTime.of(2021, 7, 30, 17, 0, 0));
        ResponseResult result =glucoseService.getGlucose(timeRangeDTO);
        assertEquals("终止时间不能早于起始时间", result.getMessage());
    }

    @Test
    @Story("血糖信息查找")
    @DisplayName("测试输入时间范围内无血糖记录的情况")
    @Description("返回空列表")
    void getGlucose3() {
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
    @Story("血糖信息查找")
    @DisplayName("测试输入时间范围内有血糖记录的情况")
    @Description("返回正确的血糖值列表")
    void getGlucose4() {
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
    @Story("血糖信息删除")
    @DisplayName("测试输入的id参数为空的情况")
    @Description("应当返回id不能为空的提示消息")
    void deleteGlucose1() {
        StpUtil.login(1);
        ResponseResult result = glucoseService.deleteGlucose(null);
        assertEquals("id不能为空", result.getMessage());
    }

    @Test
    @Story("血糖信息删除")
    @DisplayName("测试输入的id无对应的血糖记录的情况")
    @Description("应当返回血糖数据不存在的提示消息")
    void deleteGlucose2() {
        StpUtil.login(1);
        ResponseResult result = glucoseService.deleteGlucose(1L);
        assertEquals("该血糖数据不存在", result.getMessage());
    }

    @Test
    @Story("血糖信息删除")
    @DisplayName("测试输入的id有对应的血糖记录的情况")
    @Description("应当返回删除成功的提示消息")
    void deleteGlucose3() {
        StpUtil.login(1);
        ResponseResult result = glucoseService.deleteGlucose(659L);
        assertEquals("操作成功", result.getMessage());
    }

    @Test
    @Story("血糖信息更新")
    @DisplayName("测试输入的id参数为空的情况")
    @Description("应当返回id不能为空的提示消息")
    void updateGlucose1() {
        StpUtil.login(1);
        GlucoseDTO glucoseDTO=new GlucoseDTO();
        ResponseResult result = glucoseService.updateGlucose(glucoseDTO);
        assertEquals("id不能为空", result.getMessage());
    }

    @Test
    @Story("血糖信息更新")
    @DisplayName("测试输入的id参数无对应的血糖记录的情况")
    @Description("应当返回血糖数据不存在的提示消息")
    void updateGlucose2() {
        StpUtil.login(1);
        GlucoseDTO glucoseDTO=new GlucoseDTO();
        glucoseDTO.setId(1L);
        ResponseResult result = glucoseService.updateGlucose(glucoseDTO);
        assertEquals("该血糖数据不存在", result.getMessage());
    }

    @Test
    @Story("血糖信息更新")
    @DisplayName("测试输入的id参数有对应的血糖记录且gluValue和time中至少一个为空的情况")
    @Description("应当返回修改成功的提示消息")
    void updateGlucose3() {
        StpUtil.login(1);
        GlucoseDTO glucoseDTO=new GlucoseDTO();
        glucoseDTO.setId(658L);
        glucoseDTO.setGluValue(new BigDecimal("14.000"));
        ResponseResult result = glucoseService.updateGlucose(glucoseDTO);
        assertEquals("操作成功", result.getMessage());
    }

    @Test
    @Story("血糖信息更新")
    @DisplayName("测试输入的id参数有对应的血糖记录且gluValue和time均不为空的情况")
    @Description("应当返回修改成功的提示消息")
    void updateGlucose4() {
        StpUtil.login(1);
        GlucoseDTO glucoseDTO=new GlucoseDTO();
        glucoseDTO.setId(657L);
        glucoseDTO.setGluValue(new BigDecimal("13.800"));
        glucoseDTO.setTime(LocalDateTime.of(2021,8,6,12,30,0));
        ResponseResult result = glucoseService.updateGlucose(glucoseDTO);
        assertEquals("操作成功", result.getMessage());
    }

    @Test
    @Story("血糖信息添加")
    @DisplayName("测试输入的gluValue或time为空的情况")
    @Description("应当返回血糖值和时间不能为空的提示消息")
    void addGlucose1() {
        StpUtil.login(1);
        GlucoseAddDTO glucoseAddDTO=new GlucoseAddDTO();
        glucoseAddDTO.setGluValue(new BigDecimal("12.000"));
        ResponseResult result = glucoseService.addGlucose(glucoseAddDTO);
        assertEquals("血糖值和时间不能为空", result.getMessage());
    }

    @Test
    @Story("血糖信息添加")
    @DisplayName("测试输入的gluValue和time均不为空的情况")
    @Description("应当返回添加成功的提示消息")
    void addGlucose2() {
        StpUtil.login(1);
        GlucoseAddDTO glucoseAddDTO=new GlucoseAddDTO();
        glucoseAddDTO.setGluValue(new BigDecimal("12.000"));
        glucoseAddDTO.setTime(LocalDateTime.of(2024,6,10,12,30,0));
        ResponseResult result = glucoseService.addGlucose(glucoseAddDTO);
        assertEquals("操作成功", result.getMessage());
    }
}