package org.cv.redis.service.impl;

import com.alibaba.fastjson.JSONArray;
import org.cv.redis.entity.TestStu;
import org.cv.redis.mapper.TestStuMapper;
import org.cv.redis.service.TestStuService;
import org.cv.redis.utils.RedisServe;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Ale on 2020/4/1
 */
@Service
@Transactional
public class TestStuServiceImpl implements TestStuService {
    /**
     * @Autowired 会导致编译报错所以用Resource来取代
     */
    @Resource
    TestStuMapper stuMapper;

    @Resource
    RedisServe redisServe;

    @Override
    public TestStu findById(long id) {
        if (id < 0L) {
            throw new RuntimeException("入参错误");
        }
        return stuMapper.findById(id);
    }

    @Override
    public List<TestStu> findByAll() {
        boolean isKey = redisServe.hasKey("stuList");

        // 判断key是否存在 存在则从缓存读取数据
        if (isKey) {
            return redisServe.lGet("stuList", 0, -1);
        }

        List<TestStu> byAll = stuMapper.findByAll();
        JSONArray array = new JSONArray();
        for (TestStu testStu : byAll) {
            array.add(testStu);
        }
        boolean flag = redisServe.lSet("stuList",array);
        if (flag) {
            return stuMapper.findByAll();
        }
        throw new RuntimeException("缓存失败");
    }

    @Override
    public int addStu(TestStu stu) {
        if (stu == null) {
            throw new RuntimeException("入参错误");
        }
        return stuMapper.addStu(stu);
    }

    @Override
    public int addStuList(List<TestStu> list) {
        if (list.size() == 0) {
            throw new RuntimeException("入参错误");
        }
        return stuMapper.addStuList(list);
    }

    @Override
    public int delStu(long id) {
        if (id < 0L) {
            throw new RuntimeException("入参错误");
        }
        return stuMapper.delStu(id);
    }

    @Override
    public int delStuList(List<Long> ids) {
        if (ids.size() == 0) {
            throw new RuntimeException("入参错误");
        }
        return stuMapper.delStuList(ids);
    }
}
