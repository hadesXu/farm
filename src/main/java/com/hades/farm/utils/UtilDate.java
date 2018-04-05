package com.hades.farm.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtilDate {
    private static final Log LOGGER = LogFactory.getLog(UtilDate.class);

    /**
     * 产生随机数
     *
     * @return
     */
    public static String getRandomNum(Integer num) {
        Random rad = new Random();
        return rad.nextInt(num) + "";
    }

    /**
     * 获取请求参数集合
     *
     * @param request
     * @return
     */
    public static Map<String, String> getParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();

        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }

            if (valueStr != null && !"".equals(valueStr)) {
                params.put(name, valueStr);
            }

        }
        return params;
    }

}
