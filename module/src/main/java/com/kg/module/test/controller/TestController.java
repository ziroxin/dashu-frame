package com.kg.module.test.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ziro
 * @date 2022/4/28 21:04
 */
@RestController
public class TestController {

    @ApiOperation(value = "test", notes = "test", httpMethod = "GET")
    @GetMapping("/test/hello")
    public String hello() {
        return "hello";
    }
}
