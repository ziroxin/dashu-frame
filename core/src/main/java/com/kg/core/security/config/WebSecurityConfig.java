package com.kg.core.security.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import com.kg.core.filter.JwtTokenAuthenticationFilter;
import com.kg.core.security.handler.SimpleAccessDeniedHandler;
import com.kg.core.security.handler.SimpleAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serurity 配置
 *
 * @author ziro
 * @date 2022/4/27 23:08
 */
@Configuration
// 允许security注解权限标记
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
    @Autowired
    private SimpleAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private SimpleAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 配置默认解密方法
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置AuthenticationManager
     * （这样就可以在自己的service中注入了）
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 读取忽略列表
        List<String> antMatchers = FileUtil.readLines("security.ignore", CharsetUtil.defaultCharset());
        String[] ignoreUrls = antMatchers.stream()
                .filter(url -> StringUtils.hasText(url) && !url.startsWith("#"))
                .collect(Collectors.toList()).toArray(new String[]{});

        // 配置Security
        http
                // 关闭跨站请求防护（前后端分离项目）
                .csrf().disable()
                // 关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 配置忽略认证的请求（如登录页）
                .authorizeRequests()
                .antMatchers(ignoreUrls).permitAll()
                // 其他请求，均需认证
                .anyRequest().authenticated();

        // 配置JwtToken过滤器，早于用户名密码验证过滤器
        http.addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 配置异常的拦截器
        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);
    }
}
