package org.cv.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ale on 2020/4/1
 */
@Controller
@RequestMapping("/sys")
public class TestController {

//    @ResponseBody
//    @RequestMapping("/sys")
//    public ResponseMsg<Object> onConnect(){
//        // 进行链接
//
//
//
//
//        // 默认是false
//        return new ResponseMsg<>();
//    }


    @RequestMapping("/index")
    public String onIndex(){
        return "index";
    }

    @RequestMapping("/admin")
    public String onAdmin(){
        return "admin/admin";
    }

}
