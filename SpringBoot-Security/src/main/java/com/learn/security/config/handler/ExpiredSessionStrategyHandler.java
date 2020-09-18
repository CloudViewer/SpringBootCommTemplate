package com.learn.security.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.security.response.ResponseData;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by Ale on 2020/9/17
 */
public class ExpiredSessionStrategyHandler implements SessionInformationExpiredStrategy {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        // 当session 被多设备登录的处理
        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write(objectMapper.writeValueAsString(ResponseData.systemError("您的账号已在别的设备登录，请重新登录~","","1")));
    }
}
