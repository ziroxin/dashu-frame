package com.kg.component.utils;

import java.util.UUID;

/**
 * UUID生成工具
 *
 * @author ziro
 * @date 2022-05-14 10:16:59
 */
public class GuidUtils {

    /**
     * 获取36位uuid
     */
    public static String getUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取32位uuid，不带 -
     */
    public static String getUuid32() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
