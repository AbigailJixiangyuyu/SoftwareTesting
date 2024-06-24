package com.tongji.user.service.Impl;

import cn.dev33.satoken.stp.StpUtil;

import com.tongji.model.vo.ResponseResult;
import com.tongji.user.UserServiceApplication;

import com.tongji.model.pojo.User;
import com.tongji.model.pojo.UserDetail;
import com.tongji.model.dto.LoginDTO;
import com.tongji.model.dto.TimeRangeDTO;
import com.tongji.model.dto.UserDTO;
import com.tongji.model.dto.UserDetailDTO;
import com.tongji.common.service.Impl.CacheService;
import com.tongji.common.constants.CommonConstants;
import com.tongji.common.enums.AppHttpCodeEnum;
import com.tongji.common.utils.SmsUtil;


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



@SpringBootTest(classes = UserServiceApplication.class)
@Feature("子系统间注册功能测试")
class InRegisterSystemTest {
    @Autowired
    private CacheService cacheService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private SmsUtil smsUtil;

     @Test
    @Story("注册与发送验证码")
    @DisplayName("测试正常注册和子系统内获取验证码")
    @Description("返回正常注册的信息")
    void registerTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Chong");
        userDTO.setAccount("17341397501");
        userDTO.setPassword("269750");
        userService.sendCode("17341397501");
        userDTO.setCode(cacheService.get(CommonConstants.SMS_CODE + "17341397501"));
        ResponseResult result = userService.register(userDTO);
       assertEquals("操作成功", result.getMessage());
    }

   

}
