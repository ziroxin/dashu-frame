package com.kg.core.security.util;

import com.kg.core.security.entity.SecurityUserDetailEntity;
import com.kg.core.zuser.entity.ZUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 获取当前登录用户
 *
 * @author ziro
 * @date 2022/5/9 22:32
 */
public class CurrentUserUtils {

    /**
     * @return 获取当前用户信息
     */
    public static ZUser getCurrentUser() {
        SecurityUserDetailEntity userDetailEntity = (SecurityUserDetailEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetailEntity.getZUser();
    }


}
