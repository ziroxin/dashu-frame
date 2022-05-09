package com.kg.core.zlogin.service.impl;

import com.kg.component.jwt.JwtUtils;
import com.kg.component.redis.RedisUtils;
import com.kg.core.common.constant.LoginConstant;
import com.kg.core.exception.BaseException;
import com.kg.core.security.entity.SecurityUserDetailEntity;
import com.kg.core.zlogin.dto.LoginSuccessDTO;
import com.kg.core.zlogin.service.ZLoginService;
import com.kg.core.zuser.entity.ZUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
    public LoginSuccessDTO login(ZUser zUser) throws BaseException {
        // AuthenticationManager 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(zUser.getUserName(), zUser.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 认证不通过
        if (ObjectUtils.isEmpty(authenticate)) {
            throw new BaseException("登录失败，用户名或密码错误！");
        }
        // 获取登录成功的userId
        SecurityUserDetailEntity userDetailEntity = (SecurityUserDetailEntity) authenticate.getPrincipal();
        String userId = userDetailEntity.getZUser().getUserId();
        // 生成JwtToken
        LoginSuccessDTO loginSuccessDTO = new LoginSuccessDTO();
        loginSuccessDTO.setAccessToken(JwtUtils.createToken(userId));
        // 把用户信息存入redis
        redisUtils.set(LoginConstant.LOGIN_INFO_REDIS_PRE + userId, userDetailEntity);
        loginSuccessDTO.setSuccessMsg("登录成功！");
        return loginSuccessDTO;
    }

    @Override
    public void logout() {
        // 从SecurityContextHolder中获取用户信息
        SecurityUserDetailEntity userDetailEntity =
                (SecurityUserDetailEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetailEntity.getZUser().getUserId();
        // 清空redis中的登录信息
        redisUtils.delete(LoginConstant.LOGIN_INFO_REDIS_PRE + userId);
        // 清空上下文
        SecurityContextHolder.clearContext();
    }
}
