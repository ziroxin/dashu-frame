package com.kg.core.zapi.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kg.core.base.controller.BaseController;
import com.kg.core.zapi.entity.ZApi;
import com.kg.core.zapi.service.IZApiService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * API信息表 前端控制器
 * </p>
 *
 * @author ziro
 * @since 2022-05-05
 */
@RestController
@RequestMapping("api")
public class ZApiController implements BaseController {
    @Autowired
    private IZApiService apiService;

    @ApiOperation(value = "api/list", notes = "查询扫描到的所有API接口", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query", required = false, dataType = "String")
    })
    @GetMapping("list")
    @PreAuthorize("hasAuthority('api:list')")
    public Page<ZApi> list(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                           @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return apiService.page(new Page<>(page, limit));
    }

    @ApiOperation(value = "api/getAllApiList", notes = "查询扫描到的所有API接口", httpMethod = "GET")
    @GetMapping("getAllApiList")
    @PreAuthorize("hasAuthority('api:getAllApiList')")
    public List<ZApi> getAllApiList() {
        return apiService.getZApiList();
    }

    @ApiOperation(value = "api/saveScanApi", notes = "保存扫描到的API（已存在的不再保存）", httpMethod = "GET")
    @GetMapping("saveScanApi")
    @PreAuthorize("hasAuthority('api:saveScanApi')")
    public void saveScanApi() {
        apiService.saveScanApi();
    }


}
