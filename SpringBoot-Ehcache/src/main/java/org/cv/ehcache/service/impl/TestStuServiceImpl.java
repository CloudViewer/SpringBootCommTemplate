package org.cv.ehcache.service.impl;

import org.cv.ehcache.entity.TestStu;
import org.cv.ehcache.mapper.TestStuMapper;
import org.cv.ehcache.service.TestStuService;
import org.springframework.cache.annotation.Cacheable;
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

    @Override
    public TestStu findById(long id) {
        if (id < 0L) {
            throw new RuntimeException("入参错误");
        }
        return stuMapper.findById(id);
    }

    @Override
    @Cacheable(value = "testStu")
    public List<TestStu> findByAll() {
        return stuMapper.findByAll();
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
