package com.tongji.service.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.tongji.model.vo.ResponseResult;
import com.tongji.service.FoodServiceApplication;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = FoodServiceApplication.class)
@Feature("血糖记录模块单元测试")
class GlucoseServiceImplTest {

    @Autowired
    GlucoseServiceImpl glucoseService;

    @Test
    @Story("用例名称：血糖信息查找")
    @DisplayName("查找时间范围为空")
    @Description("应当输出时间范围不能为空的提示")
    void getGlucose1() {
        StpUtil.login(1);
        ResponseResult result =glucoseService.getGlucose(null);
        assertEquals("时间范围不能为空", result.getMessage());
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