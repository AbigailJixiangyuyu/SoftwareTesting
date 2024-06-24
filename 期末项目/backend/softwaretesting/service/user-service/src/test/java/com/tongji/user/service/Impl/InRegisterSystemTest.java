package com.tongji.user.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;

import com.tongji.model.vo.ResponseResult;
import com.tongji.user.UserServiceApplication;

import com.tongji.model.pojo.User;
import com.tongji.model.pojo.UserDetail;
import com.tongji.model.dto.LoginDTO;
import com.tongji.model.dto.TimeRangeDTO;
import com.tongji.model.dto.UserDTO;
import com.tongji.model.dto.UserDetailDTO;
import com.tongji.common.service.Impl.CacheService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tongji.common.constants.CommonConstants;
import com.tongji.common.enums.AppHttpCodeEnum;



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
@Feature("子系统内注册功能测试")
class InRegisterSystemTest {
    @Autowired
    private CacheService cacheService;
    @Autowired
    private UserServiceImpl userService;
     @Autowired
    private UserDetailServiceImpl userDetailService;

     @Test
    @Story("注册")
    @DisplayName("测试正常注册和子系统内获取验证码")
    @Description("返回正常注册的信息")
    void registerTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("RSRB");
        userDTO.setAccount("15584241069");
        userDTO.setPassword("269750");
           // 生成验证码
        String code = RandomUtil.randomNumbers(CommonConstants.SMS_CODE_LENGTH);
        // 缓存验证码
        cacheService.setEx(CommonConstants.SMS_CODE + "15584241069", code, CommonConstants.SMS_CODE_TIMEOUT,
                CommonConstants.SMS_CODE_TIMEOUT_TYPE);
        userDTO.setCode(cacheService.get(CommonConstants.SMS_CODE + "15584241069"));
        ResponseResult result = userService.register(userDTO);
        assertEquals("操作成功", result.getMessage());
    }

    @Test
    @Story("验证UserDetail")
    @DisplayName("测试注册后子系统内的UserDetail调用")
    @Description("返回调用成功的信息")
    void userDetailTest() {
        UserDetail userDetail = new UserDetail();
        User user = new User();
        user.setAccount("15584241069");
        userDetail.setUserId(user.getId());
        userDetailService.save(userDetail);
         // 从数据库中重新获取保存的用户详细信息
        UserDetail savedUserDetail = userDetailService.getOne(
            Wrappers.<UserDetail>lambdaQuery().eq(UserDetail::getUserId, user.getId())
        );
    
        // 断言用户详细信息被正确保存
        assertEquals(userDetail.getUserId(), savedUserDetail.getUserId());
        
    }

}
