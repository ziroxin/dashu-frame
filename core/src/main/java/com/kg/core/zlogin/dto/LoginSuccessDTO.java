package com.kg.core.zlogin.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ziro
 * @date 2022/5/2 22:17
 */
@Getter
@Setter
public class LoginSuccessDTO {
    // 登录成功信息
    private String successMsg;
    // 登录获得的Token
    private String accessToken;
}
