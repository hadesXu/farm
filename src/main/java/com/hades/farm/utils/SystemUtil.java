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

    public static void main(String[] args) {
        System.out.println(getUUID32());
    }
}
