package com.infinitus.bms_oa.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Slf4j
public class Httputil {
    private static final int SUCCESS_STATUS = 200;

    public static JSONObject doPostJson(String url, String json, String header, String token) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json, "UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json;charset=utf-8");
            httpPost.setHeader(header, token);
            httpPost.setEntity(s);
            HttpResponse res = client.execute(httpPost);
            if (SUCCESS_STATUS == res.getStatusLine().getStatusCode()) {
                String result = EntityUtils.toString(res.getEntity());
                response = JSONObject.parseObject(result);
            } else {
                String result = EntityUtils.toString(res.getEntity());
                response = JSONObject.parseObject(result);
                log.error("【doPostJson】返回异常结果,response:{}", response.toString());
                return response;
            }
        } catch (Exception e) {
            log.error("【doPostJson】异常,e:{}", e);
        }
        return response;
    }

}
