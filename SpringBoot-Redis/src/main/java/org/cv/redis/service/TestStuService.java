package org.cv.redis.service;

import org.cv.redis.entity.TestStu;

import java.util.List;

/**
 * Created by Ale on 2020/4/1
 */
public interface TestStuService {
    TestStu findById(long id);

    List<TestStu> findByAll();

    int
    addStu(TestStu stu);

    int addStuList(List<TestStu> list);

    int delStu(long id);

    int delStuList(List<Long> ids);
}
