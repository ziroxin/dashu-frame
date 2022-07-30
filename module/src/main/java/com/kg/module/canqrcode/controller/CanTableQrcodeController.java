package com.kg.module.canqrcode.controller;


import com.kg.module.canqrcode.entity.CanTableQrcode;
import com.kg.module.canqrcode.service.ICanTableQrcodeService;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 餐座二维码信息表 前端控制器
 * </p>
 *
 * @author ziro
 * @since 2022-07-28
 */
@RestController
@RequestMapping("table/qrcode")
public class CanTableQrcodeController {
    @Autowired
    private ICanTableQrcodeService qrcodeService;

    @ApiOperation(value = "table/qrcode/generate", notes = "生成餐桌二维码", httpMethod = "GET")
    @ApiImplicitParams({})
    @GetMapping("/generate")
//    @PreAuthorize("hasAuthority('table:qrcode:generate')")
    public boolean generate(String[] tableIds) {
        return qrcodeService.generate(tableIds);
    }

    @ApiOperation(value = "table/qrcode/lookTableCode", notes = "生成餐桌二维码", httpMethod = "GET")
    @ApiImplicitParams({})
    @GetMapping("/lookTableCode")
    public CanTableQrcode lookTableCode(String tableId) {
        return qrcodeService.lookTableCode(tableId);
    }
}
