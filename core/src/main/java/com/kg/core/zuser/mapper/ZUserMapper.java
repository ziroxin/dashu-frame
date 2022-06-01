package com.kg.core.zuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kg.core.zuser.dto.ZUserRoleSaveDTO;
import com.kg.core.zuser.entity.ZUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author ziro
 * @since 2022-05-01
 */
@Repository
public interface ZUserMapper extends BaseMapper<ZUser> {

    List<ZUserRoleSaveDTO> getUserRoleList();
}
