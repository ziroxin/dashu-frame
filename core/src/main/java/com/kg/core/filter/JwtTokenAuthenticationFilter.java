package com.kg.core.filter;

import com.kg.component.jwt.JwtUtils;
import com.kg.component.redis.RedisUtils;
import com.kg.core.common.constant.LoginConstant;
import com.kg.core.exception.BaseException;
import com.kg.core.security.entity.SecurityUserDetailEntity;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证用户登录状态
 * 过滤器，使用JwtToken验证
 *
 * @author ziro
 * @date 2022/5/4 22:41
 */
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private RedisUtils redisUtils;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String jwtToken = request.getHeader(LoginConstant.LOGIN_JWT_TOKEN_KEY);
        if (!StringUtils.hasText(jwtToken)) {
            // 无token放行（后边security会判断权限）
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token
        Object userId;
        try {
            userId = JwtUtils.parseToken(jwtToken);
            if (ObjectUtils.isEmpty(userId)) {
                throw new BaseException("无效的TOKEN");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("无效的TOKEN");
        }
        // 从redis中读取用户信息
        SecurityUserDetailEntity userDetailEntity = (SecurityUserDetailEntity) redisUtils.get(LoginConstant.LOGIN_INFO_REDIS_PRE + userId);
        if (ObjectUtils.isEmpty(userDetailEntity)) {
            throw new BaseException("用户未登录");
        }
        // 存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetailEntity, null, userDetailEntity.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 继续
        filterChain.doFilter(request, response);
    }
}
