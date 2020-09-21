package com.learn.security.controller;

import com.learn.security.config.smscode.SmsCode;
import com.learn.security.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Ale on 2020/9/21
 */
@Slf4j
@RestController
public class ESSmsController {

    @RequestMapping(value = "/smscode",method = RequestMethod.GET)
    public ResponseData sms(@RequestParam String mobile, HttpSession session){
        // TODO：校验用户填写的手机号码是否正确，校验是否存在系统中，
        //  存在则进行验证码生成并发放否则返回错误信息到客户端
        SmsCode smsCode = new SmsCode(
                RandomStringUtils.randomNumeric(4),mobile,60L
        );

        // TODO:调用短信提供商的发送接口 后续接上阿里云短信
        log.info(smsCode.getSmsCode().concat(" ----> ").concat(mobile));

        // TODO:后续不设置Session 改用redis作为
        session.setAttribute("sms_key",smsCode);
        return ResponseData.success();
    }
}
