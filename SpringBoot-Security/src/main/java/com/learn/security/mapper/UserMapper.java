package com.learn.security.mapper;

import com.learn.security.config.ESUserDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Ale on 2020/9/14
 */
@Mapper
public interface UserMapper {

    /**
     * 根据当前用户名查询用户信息
     * @param username
     * @return
     */
    @Select("SELECT su.username,su.`password`,su.enabled FROM sys_user su WHERE su.username = #{username} LIMIT 0,1")
    ESUserDetails findByUserName(@Param("username") String username);

    /**
     * 根据当前用户名查询用户角色
     * @param username
     * @return
     */
    @Select("SELECT sr.role_code FROM sys_role sr " +
            "LEFT JOIN sys_user_role sur ON sr.id = sur.role_id " +
            "LEFT JOIN sys_user su ON su.id = sur.user_id\n" +
            "WHERE su.username = #{username}")
    List<String> findRoleByUserName(@Param("username") String username);

    /**
     * 查询当前角色所拥有的权限信息
     * @param roleCodes
     * @return
     */
    @Select({
            "<script>",
                "SELECT sm.menu_action ",
                "FROM sys_menu sm ",
                "LEFT JOIN sys_role_menu srm ON sm.id = srm.menu_id",
                "LEFT JOIN sys_role sr ON sr.id = srm.role_id",
                "WHERE sr.role_code IN ",
                "<foreach collection='roleCodes' item='item' index='index' open='(' separator=',' close=')'>" ,
                    "#{roleCodes}",
                "</foreach>",
                "AND sm.is_leaf = 1",
            "</script>"
    })
    List<String> findAuthorityByRoleCodes(@Param("roleCodes") List<String> roleCodes);
}
