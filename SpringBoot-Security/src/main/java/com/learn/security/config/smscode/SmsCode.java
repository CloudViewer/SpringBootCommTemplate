package com.learn.security.config.smscode;

import com.sun.org.apache.regexp.internal.RE;

import java.time.LocalDateTime;

/**
 * Created by Ale on 2020/9/21
 */
public class SmsCode {
    // 短信验证码
    private String smsCode;

    // 过期时间
    private LocalDateTime expireTime;

    // 手机号码
    private String mobile;


    public SmsCode(String code, String mobile, long expireAfterSeconds) {
        this.smsCode = code;
        this.mobile = mobile;
        this.expireTime = LocalDateTime.now().plusSeconds(expireAfterSeconds);
    }

    /**
     * 是否过期
     * @return
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }

    public String getSmsCode() {
        return smsCode;
    }

    public String getMobile() {
        return mobile;
    }
}
