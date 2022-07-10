package com.kg.component.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.kg.component.file.FileNameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ziro
 * @date 2022-05-11 08:25:04
 */
@Component
public class WxGetOpenIdUtils {

    private static String WX_APP_ID;
    private static String WX_SECRET;
    private static String WX_JS_CODE_URL;

    /**
     * 微信appid
     */
    @Value("${com.kg.weixin.appid}")
    public void setWxAppId(String str) {
        WX_APP_ID = str;
    }

    /**
     * 微信Secret
     */
    @Value("${com.kg.weixin.secret}")
    public void setWxSecret(String str) {
        WxGetOpenIdUtils.WX_SECRET = str;
    }

    /**
     * 微信jscode-url
     */
    @Value("${com.kg.weixin.jscode-url}")
    public void setWxJsCodeUrl(String str) {
        WxGetOpenIdUtils.WX_JS_CODE_URL = str;
    }

    /**
     * 小程序获取微信用户openId
     *
     * @param js_code 小程序获取的用户code
     */
    public static String getOpenId(String js_code) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", WX_APP_ID);
        paramMap.put("secret", WX_SECRET);
        paramMap.put("js_code", js_code);
        paramMap.put("grant_type", "authorization_code");
        // 请求微信
        String result = HttpUtil.get(WX_JS_CODE_URL, paramMap);
        JSONObject obj = JSONUtil.parseObj(result);
        String openId = obj.getStr("openid");
        if (StringUtils.hasText(openId)) {
            // 请求成功
            return openId;
        }
        return null;
    }
}
