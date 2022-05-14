package com.kg.core.zapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kg.component.utils.GuidUtils;
import com.kg.core.zapi.entity.ZApi;
import com.kg.core.zapi.mapper.ZApiMapper;
import com.kg.core.zapi.service.IZApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
            List<ZApi> list = list();
            return list.stream().map(zApi -> zApi.getApiPermission()).collect(Collectors.toList());
        }
        return zApiMapper.listApiByUserId(userId);
    }

    @Override
    public void saveScanApi(List<ZApi> zApiList) {
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
            Optional<Integer> maxOrder = list.stream().map(zApi1 -> zApi1.getApiOrder())
                    .distinct().sorted((o1, o2) -> o2 - o1).findFirst();
            zApi.setApiOrder(maxOrder.isPresent() ? maxOrder.get() + 1 : 1);
            zApi.setCreateTime(LocalDateTime.now());
            return zApi;
        }).collect(Collectors.toList());
        // 保存
        saveBatch(saveList);
    }

}
