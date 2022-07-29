package com.kg.component.weixin.xcx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信小程序相关配置
 *
 * @author ziro
 * @date 2022-07-28 20:37:43
 */
@Component
public class WxXcxConfig {
    public static String WX_APP_ID;
    public static String WX_SECRET;
    // 微信auth.getAccessToken地址
    public static String WX_GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    // 微信jscode-url地址
    public static String WX_JS_CODE_URL = "https://api.weixin.qq.com/sns/jscode2session";
    public static String WX_GET_QRCODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";

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
        WX_SECRET = str;
    }
}
