package com.learn.security.config;

import com.learn.security.mapper.UserMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ale on 2020/9/18
 */
@Component
public class ESUserDetailsService implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 加载基础用户信息
        ESUserDetails userDetails = userMapper.findByUserName(username);

        // 加载用户角色列表
        List<String> roleCodes = userMapper.findRoleByUserName(username);

        // 通过用户角色列表加载用户的资源权限列表
        List<String> authority = userMapper.findAuthorityByRoleCodes(roleCodes);

        // 角色时一个特殊的权限 Security定义的角色以ROLE_开头 而数据库设计中则没有这样设计，所以此处加上前缀
        List<String> collect = roleCodes.stream().map("ROLE_"::concat).collect(Collectors.toList());
        authority.addAll(collect);

        userDetails.setAuthorities(
                AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",",authority))
        );
        return userDetails;
    }
}
