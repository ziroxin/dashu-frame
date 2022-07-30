package com.kg.module.canqrcode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kg.module.canqrcode.entity.CanTableQrcode;

import java.util.List;

/**
 * <p>
 * 餐座二维码信息表 服务类
 * </p>
 *
 * @author ziro
 * @since 2022-07-28
 */
public interface ICanTableQrcodeService extends IService<CanTableQrcode> {

    /**
     * 生成餐桌二维码
     *
     * @param tableIds 餐桌ids
     * @return 生成成功
     */
    boolean generate(String[] tableIds);

    CanTableQrcode lookTableCode(String tableId);
}
