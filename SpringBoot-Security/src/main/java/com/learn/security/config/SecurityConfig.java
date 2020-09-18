package com.learn.security.config;

import com.learn.security.config.handler.ErrorHandler;
import com.learn.security.config.handler.ExpiredSessionStrategyHandler;
import com.learn.security.config.handler.SuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * Created by Ale on 2020/9/14
 * enableGlobalMethodSecurity 注解 prePostEnabled = true 表示开启方法级别安全控制
 * 其中有四个注解表示安全控制
 * @PreAuthorize("hasRole('admin')"): 在方法执行之前 表示当前登录用户角色为admin时才能访问，否则抛出异常
 * @PostAuthorize() 在方法执行之后 根据返回值进行验证 返回
 * @PreFilter(filterTarget:过滤参数,value:过滤规则) 在执行方法之前过滤
 * PostFilter() 在执行方法之后过滤 并返回
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法安全设置
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);


    @Resource
    private SuccessHandler successHandler;

    @Resource
    private ErrorHandler errorHandler;

    @Resource
    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 认证方式 1:
         * httpBasic 认证模式(不推荐)
         */
        // http.httpBasic()
        // .and()
        // .authorizeRequests().anyRequest().authenticated(); // 针对所有请求都需要认证

        /**
         * 认证方式 2:
         * formLogin
         * SpringSecurity会帮我们验证用户名密码，所以我们不在需要编写控制器去验证，
         * 只需在SpringSecurity配置登录地址即可
         */
        // 1 登录验证配置 关闭跨站访问攻击
        http.rememberMe()
                .rememberMeParameter("remember_me_new") // 参数设置
                .rememberMeCookieName("ESRememberCookie") // cookie名称
                .tokenValiditySeconds(2 * 24 * 60 * 60)   // 过期时间,多长时间不用再次登录
                .and()
                    .csrf().disable()
                .formLogin()
                    .loginPage("/login.html")       // 配置登录页 自定义的登录页不用Thymeleaf模板 否则会出现405异常(原因待排查)
                    .loginProcessingUrl("/login")   // 配置登录认证处理
                    .usernameParameter("username")
                    .passwordParameter("password")
                    /**
                     * TODO: 配置登录成功后的跳转地址
                     * defaultSuccessUrl("/index") 默认的成功跳转页面， 登录认证成功后,SpringSecurity继续访问index.html页面,访问成功但是会出现302。
                     * 所以一般会在访问成功的Handler根据项目进行判断选取访问成功后的行为
                     * 注意：defaultSuccessUrl 与 successHandler 不能一起使用
                     * 原因：defaultSuccessUrl 默认是登录成功做页面跳转的配置而successHandle 是用于自定义登录成功的定义
                     * 二者只能选一，一般情况下successHandler 更适用于前后端分离的系统，而 defaultSuccessUrl 则反之
                     * failureUrl:登录错误的处理页面 与defaultSuccessUrl同理
                     */
//                    .failureUrl("/error")
//                    .defaultSuccessUrl("/index")
                    .successHandler(successHandler)
                    .failureHandler(errorHandler)
                .and()
                    // 2 资源文件权限配置
                    .authorizeRequests()                                              // 配置请求处理
                    /**
                     *  TODO:动态加载资源鉴权
                     */
                    .antMatchers("/login.html","/login").permitAll()      // 配置不需要登录的地址进行授权访问
                    .antMatchers("/index").authenticated()
                    // 权限表达式
                    .anyRequest().access("@rbacService.hasPermission(request,authentication)")

                    /*.antMatchers("/biz1","/biz2")
                    .hasAnyAuthority("ROLE_common","ROLE_admin") // 给biz1/biz2 添加访问权限,具有user与admin这个角色才有权限访问 biz1/biz2; ROLE_ 前缀是SpringSecurity对于角色的一种特殊前缀
                    .antMatchers("/sysuser","/syslog")
                    .hasAnyAuthority("ROLE_admin")     // 希望有admin 角色才能访问 sysuser/syslog
                    .anyRequest()
                    .authenticated()*/
                .and()
                    /**
                     *  TODO: 前后端分离时应禁用session 采用 stateless 模式
                     *  SpringSecurity 的 Session 管理
                     *  always: 如果当前请求没有 session 存在，SpringSecurity 会创建一个 session
                     *  never: SpringSecurity 将永远不会主动创建 session，但是如果 session 已存在将会使用该 session
                     *  ifRequired: 在需要时才会创建 session (默认)
                     *  stateless: SpringSecurity 不会创建或者使用任何 session。是用于接口型的无状态应用，该方式会节省资源
                     */
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

                    /**
                     * session 安全保护机制：
                     *  migrateSession: 迁移 session,重新把 session 复制一份然后在生成一个新的 session（默认保护机制 比较安全）
                     *  changeSessionId: 不会重新复制 session 只是把session 重新生成一个id
                     *  newSession: 完全不会复制之前 session,直接重新创建一个新的session
                     *  none: 不会做任何改变，不安全
                     */
                    .sessionFixation().migrateSession()
                    .invalidSessionUrl("/login.html")  // session 超时处理（过期处理）跳转到登录页
//                    .invalidSessionStrategy(new MyInvalidSessionStrategy())
                    .maximumSessions(1)                // 最大的 session 数量 最多只能有一个用户登录此账号
                    .maxSessionsPreventsLogin(false)   // 提供 session 保护处理 true:表示登录之后不允许再次登录，false:允许再次登录但是已登录的会被下线
                    .expiredSessionStrategy(new ExpiredSessionStrategyHandler()); // 当session 被多设备登录的处理
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("password encode ---> " + passwordEncoder().encode("123456"));
        // 3 配置用户以及角色
        /**
         * TODO:如需动态加载用户权限则需 实现两个接口
         * 1、UserDetailsService：
         *      loadUserByName 通过用户名加载用户、角色、权限信息 返回 UserDetails
         * 2、UserDetails:
         *   作为bean接收加载的信息
         */
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder().encode("123456"))
//                .roles("user")
//                .and()
//                .withUser("admin")
//                .password(passwordEncoder().encode("123456"))
//                .roles("admin")
//                .and()
//                .passwordEncoder(passwordEncoder());
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 静态资源访问配置 不被security拦截
        web.ignoring().antMatchers("/css/**","/js/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        // 对密码进行编码解码
        return new BCryptPasswordEncoder();
    }
}
