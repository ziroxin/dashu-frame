package com.kg.core.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 登录相关常量
 *
 * @author ziro
 * @date 2022/5/4 23:02
 */
@Component
public class LoginConstant {
    // 登录信息存储到reids的前缀
    public static final String LOGIN_INFO_REDIS_PRE = "zlogin@";
    // 登录token的key
    public static final String LOGIN_JWT_TOKEN_KEY = "z_jwt_token";
    // 权限信息存储到redis的前缀
    public static final String PERMISSION_REDIS_PRE = "zpermission@";
    // 登录token的有效时间（单位：分钟）
    public static Integer LOGIN_JWT_TOKEN_EXPIRY;

    @Value("${com.kg.login.jwt.token.expiry}")
    public void setLoginJwtTokenExpiry(Integer expiry) {
        LoginConstant.LOGIN_JWT_TOKEN_EXPIRY = expiry;
    }
}
