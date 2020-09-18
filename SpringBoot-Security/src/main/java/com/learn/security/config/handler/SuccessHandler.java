package com.learn.security.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.security.response.ResponseData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ale on 2020/9/16
 */
@Component
public class SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Value("${spring.security.loginType}")
    private String loginType;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        if (loginType.equalsIgnoreCase("JSON")) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(ResponseData.success()));
        } else {
            // 调用父类的方法，跳转到登录之前的请求页面
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
