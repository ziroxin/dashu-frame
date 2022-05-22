package com.kg.core.zapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kg.core.zapi.dto.ZApiDTO;
import com.kg.core.zapi.entity.ZApi;

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
     */
    void saveScanApi();

    /**
     * 清除无效的API
     */
    void clearApi();

    /**
     * 获得扫描到的API列表
     */
    List<ZApi> getZApiList();

    /**
     * 分组查询Api列表
     *
     * @return
     */
    List<ZApiDTO> listGroupApi();
}
