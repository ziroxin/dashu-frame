package com.kg.component.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baidubce.http.ApiExplorerClient;
import com.baidubce.http.HttpMethodName;
import com.baidubce.model.ApiExplorerRequest;
import com.baidubce.model.ApiExplorerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.List;

/**
 * @author yrt
 * @date 2022/7/26 10:11
 */
@Component
public class BaiduOCR {

    @Autowired
    private BaiduAccessToken baiduAccessToken;

    public JSONArray textRecognition(String billImage) {
        try {
            String path = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";
            ApiExplorerRequest request = new ApiExplorerRequest(HttpMethodName.POST, path);

            // 设置header参数
            request.addHeaderParameter("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            // 设置query参数
            request.addQueryParameter("access_token", baiduAccessToken.GetAccessToken());

            // 设置jsonBody参数
            String jsonBody = "image=" + URLEncoder.encode(billImage, "UTF-8") + "&paragraph=true";
            request.setJsonBody(jsonBody);

            ApiExplorerClient client = new ApiExplorerClient();
            //识别结果
            ApiExplorerResponse response = client.sendRequest(request);
            // 返回结果格式为Json字符串
            System.out.println(response.getResult());
            JSONObject jsonObj = new JSONObject(response.getResult());
            JSONArray jsonArray=jsonObj.getJSONArray("words_result");
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
