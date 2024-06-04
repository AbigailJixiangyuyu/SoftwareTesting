package com.tongji.common.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * jwt令牌校验的拦截器
 */
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取拦截到的路由信息
        // 获取请求头中的参数

        String key = request.getHeader("gateway_key");
        log.info("当前拦截到的key是：{}", key);
        if (!"r8t41n2i5ifsd1g3".equals(key)) {
            throw new RuntimeException("非法请求");
        }

        //String requestURI = request.getRequestURI();
        //log.info("当前拦截到的路由是：{}", requestURI);
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        // sa-token 鉴权登录
        if (!StpUtil.isLogin()) {
            throw new RuntimeException("未登录");
        }
        return true;
    }
}
