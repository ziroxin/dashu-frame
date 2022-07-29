package com.kg.component.weixin.xcx;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.kg.component.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ziro
 * @date 2022-07-28 20:41:31
 */
@Component
public class WxXcxTokenUtils {
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取小程序access_token
     */
    public String getAccessToken() {
        try {
            if (redisUtils.hasKey("wx_xcx_access_token")) {
                return redisUtils.get("wx_xcx_access_token").toString();
            }
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("appid", WxXcxConfig.WX_APP_ID);
            paramMap.put("secret", WxXcxConfig.WX_SECRET);
            paramMap.put("grant_type", "client_credential");
            // 请求微信
            String result = HttpUtil.get(WxXcxConfig.WX_GET_ACCESS_TOKEN_URL, paramMap);
            JSONObject obj = JSONUtil.parseObj(result);
            String access_token = obj.getStr("access_token");
            if (StringUtils.hasText(access_token)) {
                // 把access_token填入redis，有效期：实际有效期-100s
                redisUtils.set("wx_xcx_access_token", access_token, (obj.getLong("expires_in") - 100));
                return access_token;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
