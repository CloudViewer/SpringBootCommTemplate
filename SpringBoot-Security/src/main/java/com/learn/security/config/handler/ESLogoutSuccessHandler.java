package com.learn.security.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.security.response.ResponseData;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义的登录处理
 * Created by Ale on 2020/9/19
 */
@Component
public class ESLogoutSuccessHandler implements LogoutSuccessHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.getWriter().write(objectMapper.writeValueAsString(ResponseData.success()));
    }
}
