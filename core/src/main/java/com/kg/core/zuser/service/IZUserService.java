package com.kg.core.zuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kg.core.zuser.dto.ZUserRoleSaveDTO;
import com.kg.core.zuser.entity.ZUser;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ziro
 * @since 2022-05-01
 */
public interface IZUserService extends IService<ZUser> {

    List<ZUserRoleSaveDTO> getUserRoleList();

    boolean add(ZUserRoleSaveDTO zUserRoleSaveDTO);

    boolean update(ZUserRoleSaveDTO zUserRoleSaveDTO);
}
