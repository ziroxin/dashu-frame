package com.kg.core.zlogin.service.impl;

import com.kg.component.jwt.JwtUtils;
import com.kg.component.redis.RedisUtils;
import com.kg.core.security.entity.SecurityUserDetailEntity;
import com.kg.core.zlogin.dto.LoginSuccessDTO;
import com.kg.core.zlogin.service.ZLoginService;
import com.kg.core.zuser.entity.ZUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author ziro
 * @date 2022/5/2 22:14
 */
@Service
public class ZLoginServiceImpl implements ZLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public LoginSuccessDTO login(ZUser zUser) {
        // AuthenticationManager 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(zUser.getUserName(), zUser.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 认证不通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败，用户名或密码错误！");
        }
        // 获取登录成功的userId
        SecurityUserDetailEntity userDetailEntity = (SecurityUserDetailEntity) authenticate.getPrincipal();
        String userId = userDetailEntity.getZUser().getUserId();
        // 生成JwtToken
        LoginSuccessDTO loginSuccessDTO = new LoginSuccessDTO();
        loginSuccessDTO.setAccessToken(JwtUtils.createToken(userId));
        // 把用户信息存入redis
        redisUtils.set("login@" + userId, userDetailEntity);
        loginSuccessDTO.setSuccessMsg("登录成功！");
        return loginSuccessDTO;
    }
}
