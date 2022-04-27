package com.kg.core.security.service;

import com.kg.core.security.model.SecurityUserDetailEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Security 用户服务实现
 *
 * @author ziro
 * @date 2022/4/27 22:27
 */
@Service
public class SecurityUserDetailServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("来这里！！！");
        return new SecurityUserDetailEntity();
    }
}
