package com.kg.core.zapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kg.component.utils.GuidUtils;
import com.kg.core.zapi.entity.ZApi;
import com.kg.core.zapi.mapper.ZApiMapper;
import com.kg.core.zapi.service.IZApiService;
import io.swagger.annotations.ApiOperation;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * API信息表 服务实现类
 * </p>
 *
 * @author ziro
 * @since 2022-05-05
 */
@Service
public class ZApiServiceImpl extends ServiceImpl<ZApiMapper, ZApi> implements IZApiService {

    @Autowired
    private ZApiMapper zApiMapper;

    @Value("${com.kg.developer.user.ids}")
    private String DeveloperUserIds;

    @Override
    public List<String> listApiByUserId(String userId) {
        if ((DeveloperUserIds + ",").contains(userId + ",")) {
            // 判断是否开发管理员，拥有全部api权限
            return zApiList.stream().map(zApi -> zApi.getApiPermission()).collect(Collectors.toList());
        }
        return zApiMapper.listApiByUserId(userId);
    }

    @Override
    public void saveScanApi() {
        // 查询数据库中已有接口
        List<ZApi> list = list();
        // 在扫描到的apiList中，排除已存在的
        List<ZApi> noList = zApiList.stream()
                .filter(zApi -> {
                    Optional<ZApi> result = list.stream().filter(api -> api.getApiPermission().equals(zApi.getApiPermission())).findAny();
                    return result.isPresent() ? false : true;
                })
                .collect(Collectors.toList());
        // 不存在的apiList，填充空字段
        List<ZApi> saveList = noList.stream().map(zApi -> {
            zApi.setApiId(GuidUtils.getUuid());
            Optional<Integer> maxOrder = list.stream()
                    .filter(zApi1 -> zApi1.getApiOrder() != null)
                    .map(zApi1 -> zApi1.getApiOrder())
                    .distinct().sorted((o1, o2) -> o2 - o1).findFirst();
            zApi.setApiOrder(maxOrder.isPresent() ? maxOrder.get() + 1 : 1);
            zApi.setCreateTime(LocalDateTime.now());
            return zApi;
        }).collect(Collectors.toList());
        // 保存
        saveBatch(saveList);
    }


    /**
     * api列表
     */
    private List<ZApi> zApiList = new ArrayList<>();

    ZApiServiceImpl() {
        // 扫描所有接口
        scanApiList("com.kg");
    }

    /**
     * 得到zApi列表
     */
    @Override
    public List<ZApi> getZApiList() {
        return zApiList;
    }

    /**
     * 扫描所有Api列表
     *
     * @param scanPackage 需要扫描的包路径
     */
    private List<ZApi> scanApiList(String scanPackage) {
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
