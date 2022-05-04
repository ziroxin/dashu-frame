package com.kg.core.zlogin.service;

import com.kg.core.exception.BaseException;
import com.kg.core.zlogin.dto.LoginSuccessDTO;
import com.kg.core.zuser.entity.ZUser;

/**
 * @author ziro
 * @date 2022/5/2 22:14
 */
public interface ZLoginService {
    LoginSuccessDTO login(ZUser zUser) throws BaseException;

    void logout();
}
