package com.kg.core.zlogin.controller;

import com.kg.core.base.controller.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录
 *
 * @author ziro
 * @date 2022/5/2 9:46
 */
@RestController
@RequestMapping("login")
public class ZLoginController implements BaseController {

    @PostMapping("login")
    public boolean login() {

        return true;
    }

}
