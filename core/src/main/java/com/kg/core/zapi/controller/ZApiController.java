package com.kg.core.zapi.controller;


import com.kg.core.base.controller.BaseController;
import com.kg.core.zapi.entity.ZApi;
import com.kg.core.zapi.service.IZApiService;
import io.swagger.annotations.ApiOperation;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @ApiOperation(value = "api/getAllApiList", notes = "查询扫描到的所有API接口", httpMethod = "GET")
    @GetMapping("getAllApiList")
    @PreAuthorize("hasAuthority('api:getAllApiList')")
    public List<ZApi> getAllApiList() {
        return zApiList;
    }

    @ApiOperation(value = "api/saveScanApi", notes = "保存扫描到的API（已存在的不再保存）", httpMethod = "GET")
    @GetMapping("saveScanApi")
    @PreAuthorize("hasAuthority('api:saveScanApi')")
    public void saveScanApi() {
        apiService.saveScanApi(zApiList);
    }

    /**
     * api列表
     */
    private List<ZApi> zApiList = new ArrayList<>();

    ZApiController() {
        // 扫描所有接口
        gatApiList("com.kg");
    }

    /**
     * 扫描所有Api列表
     *
     * @param scanPackage 需要扫描的包路径
     */
    private List<ZApi> gatApiList(String scanPackage) {
        //设置扫描路径
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(scanPackage))
                .setScanners(new MethodAnnotationsScanner()));
        //扫描包内带有@RequiresPermissions注解的所有方法集合
        Set<Method> methods = reflections.getMethodsAnnotatedWith(PreAuthorize.class);

        methods.forEach(method -> {

            PreAuthorize annotation = method.getAnnotation(PreAuthorize.class);
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);

            // 组装实体
            ZApi zApi = new ZApi();
            zApi.setApiClassName(method.getDeclaringClass().getSimpleName());
            zApi.setApiMethodName(method.getName());
            //hasAuthority('permission:delete')
            zApi.setApiPermission(annotation.value().replace("hasAuthority('", "").replace("')", ""));
            zApi.setApiName(apiOperation.notes());
            zApi.setApiRequestUrl(apiOperation.value());
            zApi.setApiRequestMethod(apiOperation.httpMethod());
            zApi.setApiDescription(apiOperation.notes());
            zApiList.add(zApi);
        });
        return zApiList;
    }


}
