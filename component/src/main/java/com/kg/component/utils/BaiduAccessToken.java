package com.kg.component.utils;

import cn.hutool.json.JSONObject;
import com.baidubce.http.ApiExplorerClient;
import com.baidubce.http.HttpMethodName;
import com.baidubce.model.ApiExplorerRequest;
import com.baidubce.model.ApiExplorerResponse;
import com.kg.component.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yrt
 * @date 2022/7/26 9:08
 */
@Component
public class BaiduAccessToken {

    @Autowired
    private RedisUtils redisUtils;

    private static String CLIENT_ID;
    private static String CLIENT_SECRET;
    private static String GRANT_TYPE;

    /**
     * 百度API Key
     */
    @Value("${com.kg.baidu.clientId}")
    public void setClientId(String str) {
        CLIENT_ID = str;
    }

    /**
     * 百度Secret Key
     */
    @Value("${com.kg.baidu.clientSecret}")
    public void setClientSecret(String str) {
        BaiduAccessToken.CLIENT_SECRET = str;
    }

    /**
     * 百度grant_type
     */
    @Value("${com.kg.baidu.grantType}")
    public void setGrantType(String str) {
        BaiduAccessToken.GRANT_TYPE = str;
    }

    // 获取AccessToken
    public String GetAccessToken() {
        try {
            if (redisUtils.hasKey("accessToken")) {
                return redisUtils.get("accessToken").toString();
            }

            String path = "https://aip.baidubce.com/oauth/2.0/token";
            ApiExplorerRequest request = new ApiExplorerRequest(HttpMethodName.POST, path);


            // 设置header参数
            request.addHeaderParameter("Content-Type", "application/json;charset=UTF-8");

            // 设置query参数
            request.addQueryParameter("client_id", CLIENT_ID);
            request.addQueryParameter("client_secret", CLIENT_SECRET);
            request.addQueryParameter("grant_type", GRANT_TYPE);


            ApiExplorerClient client = new ApiExplorerClient();
            ApiExplorerResponse response = client.sendRequest(request);
            JSONObject jsonObj = new JSONObject(response.getResult());
            String accessToken = jsonObj.get("access_token").toString();
            Long expiresIn =  jsonObj.getLong("expires_in");
            redisUtils.set("accessToken", accessToken, expiresIn - 1000);
            // 返回结果格式为Json字符串
            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
