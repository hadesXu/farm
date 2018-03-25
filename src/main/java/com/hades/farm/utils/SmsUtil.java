package com.hades.farm.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSON;
import com.hades.farm.result.ErrorCode;
import com.hades.farm.result.Result;
import com.hades.farm.utils.sms.SmsSendRequest;
import com.hades.farm.utils.sms.SmsSendResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * @author
 * @Description:HTTP 请求
 */
public class SmsUtil {
    public static final String charset = "utf-8";
    // 用户平台API账号(非登录账号,示例:N1234567)
    public static String account = "CN1035616";
    // 用户平台API密码(非登录密码)
    public static String pswd = "KR4kXijNWB8045";

    public static String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";

    public static Result<Void> sendSms(String phone, String msg) {
        Result<Void> result = Result.newResult();
        //状态报告
        String report = "true";

        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phone, report);

        String requestJson = JSON.toJSONString(smsSingleRequest);

        System.out.println("before request string is: " + requestJson);
//
//        String response = SmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
//
//        System.out.println("response after request result is :" + response);

//        SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
//        System.out.println("response  toString is :" + smsSingleResponse);
//        if (StringUtils.isNoneBlank(smsSingleResponse.getErrorMsg())) {
//            result.addError(ErrorCode.SYSTEM_ERROR);
//            return result;
//        }
        return result;
    }

    /**
     * @param path
     * @param postContent
     * @return String
     * @throws
     * @author tianyh
     * @Description
     */
    public static String sendSmsByPost(String path, String postContent) {
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
            httpURLConnection.setReadTimeout(10000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            httpURLConnection.connect();
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(postContent.getBytes("UTF-8"));
            os.flush();

            StringBuilder sb = new StringBuilder();
            int httpRspCode = httpURLConnection.getResponseCode();
            if (httpRspCode == HttpURLConnection.HTTP_OK) {
                // 开始获取数据
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        SmsUtil.sendSms("18620198505", "123test是是是");
    }

}
