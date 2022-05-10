package com.kg.core.zpermission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kg.core.zpermission.entity.ZPermission;

import java.util.Map;

/**
 * <p>
 * 资源权限表 服务类
 * </p>
 *
 * @author ziro
 * @since 2022-05-09
 */
public interface IZPermissionService extends IService<ZPermission> {

    Map<String, Object> listPermissionByUserId(String userId);
}
