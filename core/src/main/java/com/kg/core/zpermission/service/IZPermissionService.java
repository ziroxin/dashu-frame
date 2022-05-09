package com.kg.core.zpermission.service;

import com.kg.core.zpermission.entity.ZPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 资源权限表 服务类
 * </p>
 *
 * @author ziro
 * @since 2022-05-09
 */
public interface IZPermissionService extends IService<ZPermission> {

    List<ZPermission> listPermissionByUserId(String userId);
}
