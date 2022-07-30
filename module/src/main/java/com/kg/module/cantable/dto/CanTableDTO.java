package com.kg.module.cantable.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author yrt
 * @date 2022/7/29 22:47
 */
@Getter
@Setter
public class CanTableDTO {

    @ApiModelProperty("餐桌ID")
    private String tableId;

    @ApiModelProperty("店铺ID")
    private String shopId;

    @ApiModelProperty("餐桌名称")
    private String tableName;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("微信小程序二维码参数id")
    private String qrcodeId;

    @ApiModelProperty("二维码文件名")
    private String qrcodeFileName;

    @ApiModelProperty("二维码保存路径")
    private String qrcodeFilePath;

}
