package org.cv.ehcache.controller;

import org.cv.ehcache.entity.TestStu;
import org.cv.ehcache.service.TestStuService;
import org.cv.ehcache.utils.DateUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ale on 2020/4/1
 */
@RestController
public class TestController {


    @Resource()
    TestStuService stuService;


    /**
     * 查询所有学生
     * @return
     */
    @RequestMapping("/findAll")
    public  Object findAll() {
        return stuService.findByAll();
    }

    @RequestMapping("/getStu/{id}")
    public Object findById(@PathVariable long id){
        return stuService.findById(id);
    }

    @RequestMapping("/addStu")
    public Map addStu(){
        Map<String,Object> map = new HashMap<>();
        int num =  stuService.addStu(new TestStu("光头强", DateUtil.getDateTime()));
        if (num > 0) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @RequestMapping("/addList")
    public Map addStuList(){
        Map<String,Object> map = new HashMap<>();
        List<TestStu> list = new ArrayList<>();
        list.add(new TestStu("虚竹", DateUtil.getDateTime()));
        list.add(new TestStu("乔峰", DateUtil.getDateTime()));
        list.add(new TestStu("杨过", DateUtil.getDateTime()));
        int num = stuService.addStuList(list);
        if(num > 0){
            map.put("success",true);
        }else {
            map.put("success",false);
        }
        map.put("total",num);
        return map;
    }

    @RequestMapping("/delStu/{id}")
    public Map delStu(@PathVariable long id){
        Map<String,Object> map = new HashMap<>();
        int num = stuService.delStu(id);
        if (num > 0) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        map.put("total",num);
        return map;
    }

    @RequestMapping("/delStuList")
    public Map delStuList(){
        Map<String,Object> map = new HashMap<>();

        List<Long> list = new ArrayList<>();
        list.add(7L);
        list.add(8L);
        int num = stuService.delStuList(list);
        if (num > 0) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        map.put("total",num);
        return map;
    }
}
