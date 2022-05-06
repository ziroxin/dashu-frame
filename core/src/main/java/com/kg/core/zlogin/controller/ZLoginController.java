package com.kg.core.zlogin.controller;

import com.kg.core.base.controller.BaseController;
import com.kg.core.exception.BaseException;
import com.kg.core.zlogin.dto.LoginSuccessDTO;
import com.kg.core.zlogin.service.ZLoginService;
import com.kg.core.zuser.entity.ZUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户登录
 *
 * @author ziro
 * @date 2022/5/2 9:46
 */
@Api(tags = "ZLoginController", value = "用户登录", description = "用户登录控制器")
@RestController
@Validated
@RequestMapping("login")
public class ZLoginController implements BaseController {

    @Autowired
    private ZLoginService zLoginService;

    @ApiOperation(value = "登录", notes = "11", httpMethod = "POST")
    @PostMapping("login")
    public LoginSuccessDTO login(@RequestBody ZUser zUser) throws BaseException {
        return zLoginService.login(zUser);
    }

    @ApiOperation(value = "登出", notes = "2", httpMethod = "POST")
    @GetMapping("logout")
    public void logout() {
        zLoginService.logout();
    }

}
