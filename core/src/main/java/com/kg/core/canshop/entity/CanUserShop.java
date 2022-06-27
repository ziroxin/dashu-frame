package com.kg.core.canshop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户店铺中间表
 * </p>
 *
 * @author dazui
 * @since 2022-06-27
 */
@TableName("can_user_shop")
@ApiModel(value = "CanUserShop对象", description = "用户店铺中间表")
public class CanUserShop implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("店铺ID")
    @TableId(value = "shop_id")
    private String shopId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "CanUserShop{" +
            "userId=" + userId +
            ", shopId=" + shopId +
        "}";
    }
}
