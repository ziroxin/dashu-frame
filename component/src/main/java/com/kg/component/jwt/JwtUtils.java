package com.kg.component.jwt;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Jwt工具类
 *
 * @author ziro
 * @date 2022/5/2 21:57
 */
@Component
public class JwtUtils {
    // 生成Token值的name
    public static final String TOKEN_VALUE_NAME = "jwt_token_value";
    // 生成token的key
    private static final byte[] JWT_TOKEN_KEY = "com.keepgrown.dashu.frame".getBytes();
    // 生成Token的有效期（单位：分，默认：120分钟）
    private static Integer JWT_EXPIRE_TIME;

    @Value("${com.kg.login.jwt.token.expiry}")
    public void setJwtTokenKey(Integer expiry) {
        JwtUtils.JWT_EXPIRE_TIME = expiry;
    }

    /**
     * 生成 jwt_token
     *
     * @param value 待生成的值
     * @return jwt_token
     */
    public static String createToken(Object value) {
        return JWT.create().setKey(JWT_TOKEN_KEY)
                .setExpiresAt(Date.from(LocalDateTime.now().plusMinutes(JWT_EXPIRE_TIME).atZone(ZoneId.systemDefault()).toInstant()))
                .setPayload(TOKEN_VALUE_NAME, value)
                .sign();
    }

    /**
     * 解析 jwt_token
     *
     * @param token jwt_token
     * @return 解析结果
     */
    public static Object parseToken(String token) {
        if (JwtUtils.verifyToken(token)) {
            JWT jwt = JWTUtil.parseToken(token);
            return jwt.getPayload(TOKEN_VALUE_NAME);
        }
        throw new RuntimeException("Token已失效");
    }

    /**
     * 验证 jwt_token
     *
     * @param token jwt_token
     * @return jwt_token是否正确
     */
    public static boolean verifyToken(String token) {
        return JWT.of(token).setKey(JWT_TOKEN_KEY).validate(0);
    }

}
