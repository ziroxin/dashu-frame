package com.kg.core.zrole.service;

import com.kg.core.zrole.dto.ZRolePermissionSaveDTO;
import com.kg.core.zrole.entity.ZRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 * @author ziro
 * @since 2022-05-21
 */
public interface IZRolePermissionService extends IService<ZRolePermission> {

    /**
     * 保存角色权限关系
     */
    void saveRolePermission(ZRolePermissionSaveDTO rolePermissionSaveDTO);
}
