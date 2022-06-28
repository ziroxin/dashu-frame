package com.kg.module.candishes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 菜品表
 * </p>
 *
 * @author dazui
 * @since 2022-06-25
 */
@TableName("can_dishes")
@ApiModel(value = "CanDishes对象", description = "菜品表")
public class CanDishes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜品ID")
    @TableId(value = "dishes_id", type = IdType.ASSIGN_UUID)
    private String dishesId;

    @ApiModelProperty("店铺ID")
    private String shopId;

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

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("最后修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public String getDishesId() {
        return dishesId;
    }

    public void setDishesId(String dishesId) {
        this.dishesId = dishesId;
    }
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
    public String getDishesName() {
        return dishesName;
    }

    public void setDishesName(String dishesName) {
        this.dishesName = dishesName;
    }
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }
    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }
    public Integer getDishesOrder() {
        return dishesOrder;
    }

    public void setDishesOrder(Integer dishesOrder) {
        this.dishesOrder = dishesOrder;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "CanDishes{" +
            "dishesId=" + dishesId +
            ", shopId=" + shopId +
            ", dishesName=" + dishesName +
            ", introduction=" + introduction +
            ", picture=" + picture +
            ", originalPrice=" + originalPrice +
            ", currentPrice=" + currentPrice +
            ", state=" + state +
            ", recommend=" + recommend +
            ", dishesOrder=" + dishesOrder +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
