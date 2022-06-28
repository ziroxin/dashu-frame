package com.kg.module.canshop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kg.core.base.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author yrt
 * @date 2022/6/24 22:03
 */
@Getter
@Setter
public class CanUserShopDTO implements BaseDTO {

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("店铺ID")
    private String shopId;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("宣传图片")
    private String sharingPhoto;

    @ApiModelProperty("店铺联系电话")
    private String shopPhone;

    @ApiModelProperty("店铺地址")
    private String shopAddress;

    @ApiModelProperty("是否显示推荐（0是，1否）")
    private String showRecommend;

    @ApiModelProperty("推荐菜单名称")
    private String recommendName;

    @ApiModelProperty("店铺状态（0开业，1休息）")
    private String shopState;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("最后修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateTime;
}
