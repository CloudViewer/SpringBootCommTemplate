package com.learn.security.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.security.response.ResponseData;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ale on 2020/9/18
 */
public class MyInvalidSessionStrategy implements InvalidSessionStrategy {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request,
                                         HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(
                ResponseData.systemError("会话已过期，请重新登录","","2")));
    }
}
