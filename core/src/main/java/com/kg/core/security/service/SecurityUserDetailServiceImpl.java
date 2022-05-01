package com.kg.core.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kg.core.security.entity.SecurityUserDetailEntity;
import com.kg.core.zuser.entity.ZUser;
import com.kg.core.zuser.mapper.ZUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * Security 用户服务实现
 *
 * @author ziro
 * @date 2022/4/27 22:27
 */
@Service
public class SecurityUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private ZUserMapper zUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        QueryWrapper<ZUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ZUser::getUserName, username);
        ZUser zUser = zUserMapper.selectOne(wrapper);
        // 未查到
        if (Objects.isNull(zUser)) {
            // todo 抛出异常
        }
        // 查到用户，并返回
        return new SecurityUserDetailEntity(zUser);
    }
}
