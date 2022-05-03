package com.kg.core.zlogin.controller;

import com.kg.core.base.controller.BaseController;
import com.kg.core.exception.BaseException;
import com.kg.core.zlogin.dto.LoginSuccessDTO;
import com.kg.core.zlogin.service.ZLoginService;
import com.kg.core.zuser.entity.ZUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录
 *
 * @author ziro
 * @date 2022/5/2 9:46
 */
@RestController
@Validated
@RequestMapping("login")
public class ZLoginController implements BaseController {

    @Autowired
    private ZLoginService zLoginService;

    @PostMapping("login")
    public LoginSuccessDTO login(@RequestBody ZUser zUser, HttpServletRequest request) throws BaseException {
        return zLoginService.login(zUser);
    }

}
