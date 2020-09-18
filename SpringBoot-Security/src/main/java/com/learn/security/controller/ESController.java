package com.learn.security.controller;

import com.learn.security.service.impl.UserviceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;


/**
 * Created by Ale on 2020/9/14
 */
@Controller
public class ESController {
    @Resource
    UserviceImpl uservice;

    @RequestMapping(value = "/index")
    public String doIndex() {
        return "index";
    }


    @RequestMapping(value = "/home")
    public String doSysLog() {
        return "home";
    }


    @RequestMapping(value = "/user_list")
    public String doSysUser() {
        uservice.findAll();
        return "user_list";
    }

    @RequestMapping(value = "/user_log")
    public String doBiz1() {
        return "user_log";
    }

    @RequestMapping(value = "/sys_log")
    public String doBiz2() {
        return "sys_log";
    }
}
