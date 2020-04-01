package org.cv.ehcache.mapper;

import org.apache.ibatis.annotations.*;
import org.cv.ehcache.entity.TestStu;
import org.cv.ehcache.utils.DynamicSqlTool;

import java.util.List;

/**
 * Created by Ale on 2020/4/1
 */
@Mapper
public interface TestStuMapper {

    /**
     * 根据ID查询
     *
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
