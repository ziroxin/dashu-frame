package com.kg.core.zapi.controller;


import com.kg.core.base.controller.BaseController;
import com.kg.core.zapi.dto.ZApiDTO;
import com.kg.core.zapi.entity.ZApi;
import com.kg.core.zapi.service.IZApiService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@Validated
public class ZApiController implements BaseController {
    @Autowired
    private IZApiService apiService;

    @ApiOperation(value = "api/listGroupApi", notes = "查询扫描到的所有API接口", httpMethod = "GET")
    @GetMapping("listGroupApi")
    @PreAuthorize("hasAuthority('api:listGroupApi')")
    public List<ZApiDTO> listGroupApi() {
        return apiService.listGroupApi();
    }

    @ApiOperation(value = "api/getAllApiList", notes = "查询扫描到的所有API接口", httpMethod = "GET")
    @GetMapping("getAllApiList")
    @PreAuthorize("hasAuthority('api:getAllApiList')")
    public List<ZApi> getAllApiList() {
        return apiService.getZApiList();
    }

    @ApiOperation(value = "api/saveScanApi", notes = "保存扫描到的API（已存在的更新）", httpMethod = "GET")
    @GetMapping("saveScanApi")
    @PreAuthorize("hasAuthority('api:saveScanApi')")
    public void saveScanApi() {
        apiService.saveScanApi();
    }

    @ApiOperation(value = "api/clearApi", notes = "清除无效的API", httpMethod = "GET")
    @GetMapping("clearApi")
    @PreAuthorize("hasAuthority('api:clearApi')")
    public void clearApi() {
        apiService.clearApi();
    }

}
