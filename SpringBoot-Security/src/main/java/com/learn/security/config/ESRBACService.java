package com.learn.security.config;

import com.learn.security.mapper.ESRBACMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Ale on 2020/9/18
 */
@Component("rbacService")
public class ESRBACService {
    private AntPathMatcher antPathMatcher= new AntPathMatcher();

    @Resource
    private ESRBACMapper esrbacMapper;

    /**
     * 判断是否拥有该请求的访问权限
     * @param request
     * @param authentication
     * @return
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            List<String> actions = esrbacMapper.findActionByUserName(((UserDetails) principal).getUsername());
           return actions.stream().anyMatch(action -> antPathMatcher.match(action,request.getRequestURI()));
        }
        return false;
    }
}
