package com.kg.module.candishes.controller;


import com.kg.component.file.FileUploadDTO;
import com.kg.component.file.ImageUploadUtils;
import com.kg.module.candishes.dto.CanDishesGroupDTO;
import com.kg.module.candishes.service.ICanDishesGroupService;
import com.kg.module.candishes.service.ICanDishesService;
import com.kg.core.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 菜品表 前端控制器
 * </p>
 *
 * @author dazui
 * @since 2022-06-25
 */
@Api(tags = "CanDishesController", value = "菜品信息", description = "菜品信息")
@RestController
@RequestMapping("dishes")
public class CanDishesController {

    @Autowired
    private ICanDishesService canDishesService;

    @Autowired
    private ICanDishesGroupService canDishesGroupService;

    @ApiOperation(value = "dishes/list", notes = "查询菜品信息", httpMethod = "GET")
    @ApiImplicitParams({})
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('dishes:list')")
    public List<CanDishesGroupDTO> List(String shopId) {
        return canDishesService.getDishesGroupList(shopId);
    }


    @ApiOperation(value = "dishes/add", notes = "添加菜品", httpMethod = "POST")
    @ApiImplicitParams({})
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('dishes:add')")
    public void add(@RequestBody CanDishesGroupDTO canDishesGroupDTO) throws BaseException {
        boolean s1 = canDishesService.dishesAdd(canDishesGroupDTO);
        if (s1) {
            throw new BaseException("添加用户失败!");
        }
    }


    @ApiOperation(value = "dishes/update", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('dishes:update')")
    public void update(@RequestBody CanDishesGroupDTO canDishesGroupDTO) throws BaseException {
        boolean s1 = canDishesService.dishesUpdate(canDishesGroupDTO);
        if (s1) {
            throw new BaseException("修改用户信息失败");
        }
    }


    @ApiOperation(value = "dishes/delete", notes = "批量删除用户", httpMethod = "DELETE")
    @DeleteMapping("delete")
    @PreAuthorize("hasAuthority('dishes:delete')")
    public void delete(@RequestBody String[] dishesIds) throws BaseException {
        boolean s1 = canDishesService.removeByIds(Arrays.asList(dishesIds));
        boolean s2 = canDishesGroupService.removeByIds(Arrays.asList(dishesIds));
        if (!s1 && !s2) {
            throw new BaseException("删除用户失败！");
        }
    }

    @PostMapping("/file")
    public List<FileUploadDTO> dishesFile(HttpServletRequest request) throws IOException {
        return ImageUploadUtils.upload(request, "dishes/file");
    }
}
