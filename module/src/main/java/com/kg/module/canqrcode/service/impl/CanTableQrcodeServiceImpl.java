package com.kg.module.canqrcode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kg.component.file.FileUploadDTO;
import com.kg.component.utils.GuidUtils;
import com.kg.component.weixin.xcx.WxXcxUtils;
import com.kg.module.canorder.entity.CanOrderDetails;
import com.kg.module.canqrcode.entity.CanTableQrcode;
import com.kg.module.canqrcode.mapper.CanTableQrcodeMapper;
import com.kg.module.canqrcode.service.ICanTableQrcodeService;
import com.kg.module.cantable.entity.CanTable;
import com.kg.module.cantable.service.ICanTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 餐座二维码信息表 服务实现类
 * </p>
 *
 * @author ziro
 * @since 2022-07-28
 */
@Service
public class CanTableQrcodeServiceImpl extends ServiceImpl<CanTableQrcodeMapper, CanTableQrcode> implements ICanTableQrcodeService {
    @Autowired
    private ICanTableService tableService;
    @Autowired
    private WxXcxUtils wxXcxUtils;
    @Autowired
    private CanTableQrcodeMapper canTableQrcodeMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean generate(String[] tableIds) {
        List<CanTableQrcode> saveList = new ArrayList<>();
        // 查询餐桌列表
        List<CanTable> canTables = tableService.listByIds(Arrays.asList(tableIds));
        for (CanTable table : canTables) {
            CanTableQrcode qrcode = new CanTableQrcode();
            qrcode.setQrcodeId(GuidUtils.getUuid32());
            qrcode.setTableId(table.getTableId());
            qrcode.setShopId(table.getShopId());
            qrcode.setCreateTime(LocalDateTime.now());
            // 获取微信二维码
            FileUploadDTO qrCodeFile = wxXcxUtils.getQRCode(qrcode.getQrcodeId());
            qrcode.setQrcodeFileName(qrCodeFile.getFileName());
            qrcode.setQrcodeFilePath(qrCodeFile.getFilePath());
            saveList.add(qrcode);
        }
        // 保存二维码信息
        saveBatch(saveList);
        return true;
    }

    @Override
    public CanTableQrcode lookTableCode(String tableId) {
        return canTableQrcodeMapper.lookTableCode(tableId);
    }
}
