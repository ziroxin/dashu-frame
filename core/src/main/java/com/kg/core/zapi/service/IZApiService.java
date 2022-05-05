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

    List<String> listApiByUserId(String userId);
}
