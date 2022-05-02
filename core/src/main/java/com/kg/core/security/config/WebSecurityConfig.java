package com.kg.core.security.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serurity 配置
 *
 * @author ziro
 * @date 2022/4/27 23:08
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
        String antUrls = StringUtils.join(antMatchers.stream()
                .filter(url -> Strings.isNotBlank(url) && !url.startsWith("#"))
                .collect(Collectors.toList()));

        // 配置Security
        http
                // 关闭跨站请求防护（前后端分离项目）
                .csrf().disable()
                // 关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 配置忽略认证的请求（如登录页）
                .authorizeRequests().antMatchers(antUrls).anonymous()
                // 其他请求，均需认证
                .anyRequest().authenticated();
    }
}
