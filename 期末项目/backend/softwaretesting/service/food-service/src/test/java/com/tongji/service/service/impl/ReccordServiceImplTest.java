package com.tongji.service.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.tongji.model.dto.RecordAddDTO;
import com.tongji.model.dto.RecordUpdateDTO;
import com.tongji.model.dto.RecordStatisticDTO;
import com.tongji.model.dto.RecordDTO;
import com.tongji.model.dto.TimeRangeDTO;
import com.tongji.model.vo.ResponseResult;
import com.tongji.service.FoodServiceApplication;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest(classes = FoodServiceApplication.class)
@Feature("记录模块单元测试")
class RecordServiceImplTest {

    @Autowired
    RecordServiceImpl recordService;

    // 测试时间范围为空的情况
    @Test
    @Story("记录信息查找")
    @DisplayName("测试时间范围为空的情况")
    @Description("返回时间范围不能为空的提示消息")
    void getRecord1() {
        StpUtil.login(1);
        TimeRangeDTO timeRangeDTO = new TimeRangeDTO();
        timeRangeDTO.setStartTime(null);
        timeRangeDTO.setEndTime(null);
        ResponseResult result = recordService.getRecord(timeRangeDTO);
        assertEquals("时间范围不能为空", result.getMessage());
    }

    // 测试输入时间范围内无记录的情况
    @Test
    @Story("记录信息查找")
    @DisplayName("测试输入时间范围内无记录的情况")
    @Description("返回空列表")
    void getRecord2() {
        StpUtil.login(1);
        TimeRangeDTO timeRangeDTO = new TimeRangeDTO();
        timeRangeDTO.setStartTime(LocalDateTime.of(2019, 12, 30, 1, 0, 0));
        timeRangeDTO.setEndTime(LocalDateTime.of(2019, 12, 30, 2, 0, 0));
        ResponseResult responseResult = recordService.getRecord(timeRangeDTO);
        assertEquals("空列表", responseResult.getData());
    }

    // 测试输入时间范围内有记录的情况
    @Test
    @Story("记录信息查找")
    @DisplayName("测试输入时间范围内有记录的情况")
    @Description("返回正确的记录列表")
    void getRecord3() {
        StpUtil.login(1);
        TimeRangeDTO timeRangeDTO = new TimeRangeDTO();
        timeRangeDTO.setStartTime(LocalDateTime.of(2021, 8, 5, 16, 0, 0));
        timeRangeDTO.setEndTime(LocalDateTime.of(2021, 8, 5, 17, 0, 0));
        ResponseResult result = recordService.getRecord(timeRangeDTO);
        // 这里需要根据实际数据填写预期结果
        assertEquals("成功获取记录", result.getMessage());
    }





    // 测试输入的id参数为空的情况
    @Test
    @Story("记录信息删除")
    @DisplayName("测试输入的id参数为空的情况")
    @Description("应当返回id不能为空的提示消息")
    void deleteRecord1() {
        StpUtil.login(1);
        ResponseResult result = recordService.deleteRecord(null);
        assertEquals("id不能为空", result.getMessage());
    }

    // 测试输入的id无对应的记录的情况
    @Test
    @Story("记录信息删除")
    @DisplayName("测试输入的id无对应的记录的情况")
    @Description("应当返回记录数据不存在的提示消息")
    void deleteRecord2() {
        StpUtil.login(1);
        ResponseResult result = recordService.deleteRecord(500L);
        assertEquals("该记录数据不存在", result.getMessage());
    }

    // 测试输入的id有对应的记录的情况
    @Test
    @Story("记录信息删除")
    @DisplayName("测试输入的id有对应的记录的情况")
    @Description("应当返回删除成功的提示消息")
    void deleteRecord3() {
        StpUtil.login(1);
        ResponseResult result = recordService.deleteRecord(1L);
        assertEquals("操作成功", result.getMessage());
    }






    // 测试输入的recordDTO参数为空的情况
    @Test
    @Story("记录信息更新")
    @DisplayName("测试输入的recordDTO参数为空的情况")
    @Description("应当返回id不能为空的提示消息")
    void updateRecord1() {
        StpUtil.login(1);
        RecordDTO recordDTO = new RecordDTO();
        recordDTO.setId(null);
        recordDTO.setCreateTime(null);
        recordDTO.setType(null);
        ResponseResult result = recordService.updateRecord(recordDTO);
        assertEquals("id不能为空", result.getMessage());
    }

    // 测试输入的recordDTO无对应的记录的情况
    @Test
    @Story("记录信息更新")
    @DisplayName("测试输入的recordDTO无对应的记录的情况")
    @Description("应当返回记录数据不存在的提示消息")
    void updateRecord2() {
        StpUtil.login(1);
        ///RecordDTO recordDTO = new RecordDTO(500L, LocalDateTime.now(), "type");
        RecordDTO recordDTO = new RecordDTO();
        recordDTO.setId(500L);
        recordDTO.setCreateTime(LocalDateTime.of(2021,8,6,12,30,0));
        recordDTO.setType("早餐");
        ResponseResult result = recordService.updateRecord(recordDTO);
        assertEquals("该记录数据不存在", result.getMessage());
    }

    // 测试输入的recordDTO有对应的记录的情况
    @Test
    @Story("记录信息更新")
    @DisplayName("测试输入的recordDTO有对应的记录的情况")
    @Description("应当返回更新成功的提示消息")
    void updateRecord3() {
        StpUtil.login(1);
        RecordDTO recordDTO = new RecordDTO();
        recordDTO.setId(1L);
        recordDTO.setCreateTime(LocalDateTime.of(2021,8,3,12,0,0));
        recordDTO.setType("午餐");
        ResponseResult result = recordService.updateRecord(recordDTO);
        assertEquals("操作成功", result.getMessage());
    }





    
    // 测试输入的recordAddDTO参数为空的情况
    @Test
    @Story("记录信息添加")
    @DisplayName("测试输入的recordAddDTO参数为空的情况")
    @Description("应当返回记录值和时间不能为空的提示消息")
    void addRecord1() {
        StpUtil.login(1);
        RecordAddDTO recordAddDTO = new RecordAddDTO();
        recordAddDTO.setCreateTime(null);
        recordAddDTO.setType(null);
        ResponseResult result = recordService.addRecord(recordAddDTO);
        assertEquals("时间和类型不能为空", result.getMessage());
    }

    // 测试输入的recordAddDTO有正确参数的情况
    @Test
    @Story("记录信息添加")
    @DisplayName("测试输入的recordAddDTO有正确参数的情况")
    @Description("应当返回添加成功的提示消息")
    void addRecord2() {
        StpUtil.login(1);
        RecordAddDTO recordAddDTO = new RecordAddDTO();
        recordAddDTO.setCreateTime(LocalDateTime.of(2024,6,17,12,0,0));
        recordAddDTO.setType("午餐");
        ResponseResult result = recordService.addRecord(recordAddDTO);
        assertEquals("操作成功", result.getMessage());
    }
}
