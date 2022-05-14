package com.kg.core.zapi.service;

import com.kg.core.zapi.entity.ZApi;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * API信息表 服务类
 * </p>
 *
 * @author ziro
 * @since 2022-05-05
 */
public interface IZApiService extends IService<ZApi> {

    /**
     * 根据userId查询用户的api权限
     *
     * @param userId 用户id
     * @return 用户api权限列表
     */
    List<String> listApiByUserId(String userId);

    /**
     * 保存扫描到的API（已存在的不再保存）
     *
     * @param zApiList 扫描到的API列表
     */
    void saveScanApi(List<ZApi> zApiList);
}
