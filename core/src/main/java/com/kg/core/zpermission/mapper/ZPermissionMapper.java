package com.kg.core.zpermission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kg.core.zpermission.entity.ZPermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 资源权限表 Mapper 接口
 * </p>
 *
 * @author ziro
 * @since 2022-05-09
 */
@Repository
public interface ZPermissionMapper extends BaseMapper<ZPermission> {

    List<ZPermission> listPermissionByUserId(String userId);

    List<ZPermission> getListById(String permissionId);
}
