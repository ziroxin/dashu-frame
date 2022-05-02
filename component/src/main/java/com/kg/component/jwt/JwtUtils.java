package com.kg.component.jwt;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类
 *
 * @author ziro
 * @date 2022/5/2 21:57
 */
public class JwtUtils {

    // 生成Token值的name
    public static final String TOKEN_VALUE_NAME = "jwt_token_value";
    // 生成token的key
    private static final byte[] JWT_TOKEN_KEY = "com.keepgrown.dashu.frame".getBytes();

    /**
     * 生成 jwt_token
     *
     * @param value 待生成的值
     * @return jwt_token
     */
    public static String createToken(Object value) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(TOKEN_VALUE_NAME, value);
        return JWTUtil.createToken(payload, JWT_TOKEN_KEY);
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
        return null;
    }

    /**
     * 验证 jwt_token
     *
     * @param token jwt_token
     * @return jwt_token是否正确
     */
    public static boolean verifyToken(String token) {
        return JWTUtil.verify(token, JWT_TOKEN_KEY);
    }

}
