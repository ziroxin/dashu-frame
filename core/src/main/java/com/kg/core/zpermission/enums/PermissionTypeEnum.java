package com.kg.core.zpermission.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 资源类型枚举
 *
 * @author ziro
 * @date 2022/5/10 21:45
 */
@Getter
@AllArgsConstructor
public enum PermissionTypeEnum {
    ROUTER(0, "路由"),
    BUTTON(1, "按钮资源"),
    LINK(2, "外链"),
    OTHER(3, "其他");
    private int code;
    private String name;
}
