package com.kg.module.test.controller;

import com.kg.component.file.FileUploadDTO;
import com.kg.component.file.FileUploadUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author ziro
 * @date 2022/4/28 21:04
 */
@RestController
public class TestController {

    @ApiOperation(value = "test/hello", notes = "test", httpMethod = "GET")
    @GetMapping("/test/hello")
    @PreAuthorize("hasAuthority('test:hello')")
    public String hello() {
        return "hello";
    }

    @ApiOperation(value = "test/hello2", notes = "test2", httpMethod = "GET")
    @GetMapping("test/hello2")
    @PreAuthorize("hasAuthority('test:hello2')")
    public String hello2() {
        return "hello2";
    }

    @PostMapping("test/file")
    public List<FileUploadDTO> testFile(HttpServletRequest request) throws IOException {
        return FileUploadUtils.uploadFile(request, "test/file");
    }
}
