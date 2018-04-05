package com.hades.farm.api.controller;

import com.hades.farm.api.view.ApiResponse;
import com.hades.farm.utils.HttpConnection;
import com.hades.farm.utils.UtilDate;
import com.hades.farm.web.config.WeChatConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * Created by xiaoxu on 2018/4/1.
 */
@RestController
@RequestMapping("/api/weChat")
public class WeChatApi {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final HttpConnection httpConnection = new HttpConnection();
    @Autowired
    private WeChatConfig weChatConfig;


    @RequestMapping("/config")
    public ApiResponse initConfig(HttpSession session) {
        ApiResponse response = new ApiResponse();
        logger.info("session:{}", session.getId());
        Map map = new HashMap();
        map.put("appId", weChatConfig.getAppId());
        map.put("appSecret", weChatConfig.getAppSecret());
        map.put("redirectUri", weChatConfig.getRedirectUri());
        response.setResult(map);
        return response;
    }

    @RequestMapping("/callback")
    public void callback(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        logger.info("session:{}", session.getId());
        logger.info("wechat callback");
        resp.setContentType("text/html;charset=utf-8");
        try {
            String code = req.getParameter("code");
            String state = req.getParameter("state");
            if (("personInfo".equals(state)) && !"".equals(code)) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("appid", weChatConfig.getAppId());
                map.put("secret", weChatConfig.getAppSecret());
                map.put("code", code);
                map.put("grant_type", "authorization_code");
                logger.debug("通过code换取网页授权access_token --start");
                String result = httpConnection.post(
                        "https://api.weixin.qq.com/sns/oauth2/access_token", map);
                JSONObject jsonObject = JSONObject.fromObject(result);

                if (jsonObject.containsKey("access_token") && jsonObject.getString("access_token") != null
                        && !"".equals(jsonObject.getString("access_token"))) {
                    logger.debug("通过code换取网页授权access_token成功");
                    String access_token = jsonObject.getString("access_token");
                    String refresh_token = jsonObject.getString("refresh_token");
                    String openid = jsonObject.getString("openid");

                    logger.debug("根据网页授权access_token拉取微信用户信息--start");
                    String userInfoStr = httpConnection.get("https://api.weixin.qq.com/sns/userinfo",
                            "access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN ");
                    JSONObject userInfoJson = JSONObject.fromObject(userInfoStr);
                    if (userInfoJson.containsKey("openid") && userInfoJson.getString("openid") != null
                            && !"".equals(userInfoJson.getString("openid"))) {
                        System.out.println("获得的用户信息：" + userInfoJson.toString());

                        PrintWriter out = resp.getWriter();
                        out.print("<script>location.href = '" + weChatConfig.getServerUrl() + "checkLogin.html?" +
                                "openId=" + userInfoJson.getString("openid") +
                                "&headImgUrl=" + userInfoJson.getString("headimgurl") +
                                "&nickName=" + userInfoJson.getString("nickname") +
                                "'</script>");
                        out.flush();
                    } else {
                        logger.debug("拉取用户信息，返回错误消息" + userInfoJson.getString("errmsg"));
                    }
                } else {
                    logger.debug("调用通过code换取网页授权access_token，返回错误消息" + jsonObject.getString("errmsg"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("出现异常", e);
        }
    }

    @RequestMapping("/checkToken")
    public void checkToken(HttpServletRequest req, HttpServletResponse resp) {
        try {
            logger.debug("签名校验-- start");
            Map<String, String> param = UtilDate.getParams(req);
            // {signature=408136fec54a4a8b02742692918f38c37f585921,
            // nonce=412296513, echostr=16575623145721929214,
            // timestamp=1497341180}
            String token = "123456abc";
            String signature = param.get("signature");
            String timestamp = param.get("timestamp");
            String nonce = param.get("nonce");
            // 1）将token、timestamp、nonce三个参数进行字典序排序
            String[] array = new String[]{token, timestamp, nonce};
            StringBuffer sb = new StringBuffer();
            // 字符串排序
            Arrays.sort(array);
            // 2）将三个参数字符串拼接成一个字符串进行sha1加密
            for (int i = 0; i < 3; i++) {
                sb.append(array[i]);
            }
            if (sb != null && !"".equals(sb)) {
                String str = sb.toString();
                logger.debug("排序拼接后字符串" + str);
                // SHA1签名生成
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(str.getBytes());
                byte[] digest = md.digest();

                StringBuffer hexstr = new StringBuffer();
                String shaHex = "";
                for (int i = 0; i < digest.length; i++) {
                    shaHex = Integer.toHexString(digest[i] & 0xFF);
                    if (shaHex.length() < 2) {
                        hexstr.append(0);
                    }
                    hexstr.append(shaHex);
                }

                logger.debug("SHA1签名串" + hexstr);
                // 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
                if (signature != null && signature.equals(hexstr.toString())) {
                    logger.debug("签名校验通过。");
                    PrintWriter out = resp.getWriter();
                    out.println(param.get("echostr"));
                    out.flush();
                }
            }
        } catch (Exception e) {
            logger.error("签名校验出错", e);
        }
        logger.debug("签名校验-- end");
    }


}
