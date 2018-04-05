package com.hades.farm.utils;

import java.util.UUID;

/**
 * Created by xiaoxu on 2018/3/12.
 */
public class SystemUtil {

    /**
     * UUID 生成
     *
     * @return
     */
    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    public static boolean isNull(String str) {
        return str == null || str.equals("null") ? true : false;
    }

    public static void main(String[] args) {
        System.out.println(getUUID32());
    }
}
