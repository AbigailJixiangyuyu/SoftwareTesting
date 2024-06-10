package com.tongji.service.service.impl;

import com.tongji.model.dto.CookBookReturnDTO;
import com.tongji.model.dto.CookbookDTO;
import com.tongji.model.vo.ResponseResult;
import com.tongji.service.service.ICookbookService;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;



@Slf4j
@SpringBootTest
@Feature("食谱模块单元测试")
class CookbookServiceImplTest {

    @Autowired
    private ICookbookService cookbookService;

    @BeforeEach
    void setUp() {
        log.info("开始食谱模块测试");
    }

    @AfterEach
    void tearDown() {
        log.info("食谱模块测试结束");
    }

    @Test
    @DisplayName("测试数据不存在")
    @Description("返回查询无信息的提示消息")
    void testNotExitsGetCookbook() {
        log.info("测试数据库无结果");
        CookbookDTO cookbookDTO = new CookbookDTO();

        cookbookDTO.setLocation("广西");
        cookbookDTO.setSeason("春天");
        cookbookDTO.setNumber(-1);

        ResponseResult result = this.cookbookService.getCookbook(cookbookDTO);
        CookBookReturnDTO cookBookReturnDTO = (CookBookReturnDTO)result.getData();
        AssertionErrors.assertNull("应该获取不到数据", cookBookReturnDTO);
    }

    @Test
    @DisplayName("测试数据存在")
    @Description("返回对应所需的食谱信息")
    void testExitsGetCookbook() {
        log.info("测试数据库有结果");
        CookbookDTO cookbookDTO = new CookbookDTO();

        cookbookDTO.setLocation("东北");
        cookbookDTO.setSeason("春季");
        cookbookDTO.setNumber(1);

        ResponseResult result = this.cookbookService.getCookbook(cookbookDTO);
        CookBookReturnDTO cookBookReturnDTO = (CookBookReturnDTO)result.getData();
        AssertionErrors.assertNotNull("测试数据应该存在", cookBookReturnDTO);
    }
}