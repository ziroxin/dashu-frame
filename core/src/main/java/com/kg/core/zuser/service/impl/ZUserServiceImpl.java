package com.kg.core.zuser.service.impl;

import com.kg.core.zuser.entity.ZUser;
import com.kg.core.zuser.mapper.ZUserMapper;
import com.kg.core.zuser.service.IZUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
