package org.cv.websocket.controller;

import org.cv.websocket.service.DbRoomService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Ale on 2020/4/1
 */
@Controller
@RequestMapping("/sys")
public class TestController {

    @Resource
    private DbRoomService service;

    @ResponseBody
    @RequestMapping("/all")
    public Object findRoomByAll(){
        return service.findByAll();
    }

    @RequestMapping("/index")
    public String onIndex(){
        return "index";
    }

    @RequestMapping("/admin")
    public String onAdmin(){
        return "admin/admin";
    }

}
