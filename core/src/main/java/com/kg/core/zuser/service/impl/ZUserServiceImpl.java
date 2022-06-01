package com.kg.core.zuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kg.component.utils.GuidUtils;
import com.kg.core.zuser.dto.ZUserRoleSaveDTO;
import com.kg.core.zuser.entity.ZUser;
import com.kg.core.zuser.entity.ZUserRole;
import com.kg.core.zuser.mapper.ZUserMapper;
import com.kg.core.zuser.service.IZUserRoleService;
import com.kg.core.zuser.service.IZUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ziro
 * @since 2022-05-01
 */
@Service
public class ZUserServiceImpl extends ServiceImpl<ZUserMapper, ZUser> implements IZUserService {

    @Autowired
    private ZUserMapper zUserMapper;

    @Autowired
    IZUserRoleService userRoleService;

    @Override
    public List<ZUserRoleSaveDTO> getUserRoleList() {
        return zUserMapper.getUserRoleList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean add(ZUserRoleSaveDTO zUserRoleSaveDTO){
        ZUser zUser = new ZUser();
        BeanUtils.copyProperties(zUserRoleSaveDTO, zUser);
        zUser.setUserId(GuidUtils.getUuid());
        zUser.setCreateTime(LocalDateTime.now());

        ZUserRole zUserRole = new ZUserRole();
        zUserRole.setUserId(zUser.getUserId());
        zUserRole.setRoleId(zUserRoleSaveDTO.getRoleId());

        boolean s1 = save(zUser);
        boolean s2 = userRoleService.save(zUserRole);

        return (!s1 && !s2);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean update(ZUserRoleSaveDTO zUserRoleSaveDTO){
        ZUser zUser = new ZUser();
        BeanUtils.copyProperties(zUserRoleSaveDTO, zUser);
        zUser.setUpdateTime(LocalDateTime.now());
        boolean s1 = updateById(zUser);

        boolean s2 = userRoleService.removeById(zUser.getUserId());

        ZUserRole zUserRole = new ZUserRole();
        zUserRole.setUserId(zUser.getUserId());
        zUserRole.setRoleId(zUserRoleSaveDTO.getRoleId());
        boolean s3 = userRoleService.save(zUserRole);

        return (!s1 && !s2 && !s3);
    }


}
