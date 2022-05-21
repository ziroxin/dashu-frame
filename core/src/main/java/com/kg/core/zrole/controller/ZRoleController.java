package com.kg.core.zrole.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kg.core.zrole.entity.ZRole;
import com.kg.core.zrole.service.IZRoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author ziro
 * @since 2022-05-21
 */
@RestController
@RequestMapping("role")
public class ZRoleController {

    @Autowired
    private IZRoleService roleService;

    @ApiOperation(value = "role/list", notes = "查询角色列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "query", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query", required = true, dataType = "Integer")
    })
    @GetMapping("list")
    @PreAuthorize("hasAuthority('role:list')")
    public Page<ZRole> list(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        Page<ZRole> pager = new Page<>(page, limit);
        return roleService.page(pager);
    }
}
