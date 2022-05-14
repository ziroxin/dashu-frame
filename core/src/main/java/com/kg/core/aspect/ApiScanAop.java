package com.kg.core.aspect;

import com.kg.core.zapi.entity.ZApi;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 扫描所有接口的切面
 *
 * @author ziro
 * @date 2022-05-12 22:17:23
 */
@Aspect
@Component
public class ApiScanAop {
    /**
     * 扫描到的所有带权限的接口
     */
    private List<ZApi> zApiList = new ArrayList<>();

    /**
     * 查询扫描到的接口列表
     *
     * @return 接口列表
     */
    public List<ZApi> getzApiList() {
        return zApiList;
    }

    /**
     * 选择权限注解作为切面的切点
     */
//    @Pointcut("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
    public void preAuthorizePointCut() {
    }

    /**
     * 切入后操作
     *
     * @param joinPoint 切点
     */
//    @Around("preAuthorizePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 查出该方法所在类
            Class clazz = Class.forName(joinPoint.getTarget().getClass().getName());
            // 查出方法对应方法
            Optional<Method> method = Arrays.stream(clazz.getMethods())
                    .filter(mth -> mth.getName().equals(joinPoint.getSignature().getName()))
                    .findFirst();
            PreAuthorize annotation = method.get().getAnnotation(PreAuthorize.class);
            ApiOperation apiOperation = method.get().getAnnotation(ApiOperation.class);

            // 组装实体
            ZApi zApi = new ZApi();
            zApi.setApiClassName(clazz.getName());
            zApi.setApiMethodName(method.get().getName());
            //hasAuthority('permission:delete')
            zApi.setApiPermission(annotation.value().replace("hasAuthority('", "").replace("')", ""));
            zApi.setApiName(apiOperation.notes());
            zApi.setApiRequestUrl(apiOperation.value());
            zApi.setApiRequestMethod(apiOperation.httpMethod());
            zApi.setApiDescription(apiOperation.notes());
            zApiList.add(zApi);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return joinPoint.proceed();
        }
    }

}
