package com.learn.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Ale on 2020/9/18
 */
@Mapper
public interface ESRBACMapper {


    @Select("" +
            "SELECT distinct menu_action FROM sys_menu sm \n" +
            "LEFT JOIN sys_role_menu srm ON sm.id = srm.menu_id \n" +
            "LEFT JOIN sys_role sr ON sr.id = srm.role_id \n" +
            "LEFT JOIN sys_user_role sur ON sr.id = sur.role_id \n" +
            "LEFT JOIN sys_user su ON su.id = sur.user_id\n" +
            "WHERE su.username = #{username} AND sm.is_leaf = 1")
    List<String> findActionByUserName(@Param("username") String username);
}
