package com.kg.core.candishes.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.kg.core.base.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author yrt
 * @date 2022/6/25 17:23
 */
@Getter
@Setter
@ApiModel(description = "菜品分组关联DTO", discriminator = "菜品分组关联DTO")
public class CanDishesGroupDTO implements BaseDTO {

    @ApiModelProperty("菜品ID")
    private String dishesId;

    @ApiModelProperty("店铺ID")
    private String shopId;

    @ApiModelProperty("分组ID")
    private String groupId;

    @ApiModelProperty("菜品名称")
    private String dishesName;

    @ApiModelProperty("菜品简介")
    private String introduction;

    @ApiModelProperty("展示图")
    private String picture;

    @ApiModelProperty("原价")
    private Double originalPrice;

    @ApiModelProperty("现价")
    private Double currentPrice;

    @ApiModelProperty("菜品状态（0上架，1下架）")
    private String state;

    @ApiModelProperty("是否推荐（0是，1否）")
    private String recommend;

    @ApiModelProperty("菜品顺序")
    private Integer dishesOrder;

    @ApiModelProperty("分组名称")
    private String groupName;

    @ApiModelProperty("分组显示顺序")
    private Integer groupOrder;

    @ApiModelProperty("图标")
    private String groupIcon;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("最后修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
