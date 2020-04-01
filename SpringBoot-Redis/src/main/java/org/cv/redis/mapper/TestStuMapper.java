package org.cv.redis.mapper;

import org.apache.ibatis.annotations.*;
import org.cv.redis.entity.TestStu;
import org.cv.redis.utils.DynamicSqlTool;

import java.util.List;

/**
 * Created by Ale on 2020/4/1
 * 批量删除与批量添加由两种方式实现
 * 批量添加是通过mapper.xml的forEach标签来遍历集合从而实现批量添加
 * 批量删除准备用注解方式来实现
 * 那么要实现注解的方式来批量删除呢Mybatis提供了@DeleteProvider()
 * 注意：方法加上此注解时方法参数一定要加上Param注解来修饰入参
 * 而此注解则有两个参数需要填写所以要新定义一个类来拼接sql
 * type: 自定义动态的sql类
 * Method: 方法名
 * 反射的强大之处有木有
 */
@Mapper
public interface TestStuMapper {
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Select("select id,name,date from test_stu where id = #{id}")
    TestStu findById(@Param("id") long id);

    /**
     * 查询所有
     * @return
     */
    @Select("select id,name,date from test_stu")
    List<TestStu> findByAll();

    /**
     * 添加单个学生对象
     * @param stu
     * @return
     */
    int addStu(TestStu stu);

    /**
     * 添加多个学生对象
     * @param list
     * @return
     */
    int addStuList(@Param("list") List<TestStu> list);

    /**
     * 通过id删除学生对象
     * @param id
     * @return
     */
    @Delete("delete from test_stu where id = #{id}")
    int delStu(long id);


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteProvider(type = DynamicSqlTool.class, method = "delById")
    int delStuList(@Param("ids") List<Long> ids);

}
