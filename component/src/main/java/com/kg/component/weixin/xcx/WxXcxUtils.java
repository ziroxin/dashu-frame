package com.kg.component.weixin.xcx;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.kg.component.file.FileSaveUtils;
import com.kg.component.file.FileUploadDTO;
import com.kg.component.redis.RedisUtils;
import com.kg.component.utils.GuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ziro
 * @date 2022-05-11 08:25:04
 */
@Component
public class WxXcxUtils {
    @Autowired
    private WxXcxTokenUtils wxXcxTokenUtils;

    /**
     * 微信小程序二维码
     *
     * @param scene 扫描二维码带的参数
     * @return 二维码保存后的文件信息
     */
    public FileUploadDTO getQRCode(String scene) {
        String accessToken = wxXcxTokenUtils.getAccessToken();
        JSONObject bodyObj = new JSONObject();
        bodyObj.set("scene", scene);
        byte[] buffer = HttpUtil.createPost(WxXcxConfig.WX_GET_QRCODE_URL + "?access_token=" + accessToken)
                .body(bodyObj.toString()).execute().bodyBytes();
        return FileSaveUtils.bufferToFile(buffer, "wxxcx/qrcode", "jpg");
    }

    /**
     * 小程序获取微信用户openId
     *
     * @param js_code 小程序获取的用户code
     */
    public static String getOpenId(String js_code) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", WxXcxConfig.WX_APP_ID);
        paramMap.put("secret", WxXcxConfig.WX_SECRET);
        paramMap.put("js_code", js_code);
        paramMap.put("grant_type", "authorization_code");
        // 请求微信
        String result = HttpUtil.get(WxXcxConfig.WX_JS_CODE_URL, paramMap);
        JSONObject obj = JSONUtil.parseObj(result);
        String openId = obj.getStr("openid");
        if (StringUtils.hasText(openId)) {
            // 请求成功
            return openId;
        }
        return null;
    }
}
