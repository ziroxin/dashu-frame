package com.kg.core.canshop.entity;

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
 * 店铺表
 * </p>
 *
 * @author dazui
 * @since 2022-06-23
 */
@TableName("can_shop")
@ApiModel(value = "CanShop对象", description = "店铺表")
public class CanShop implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店铺ID")
    @TableId(value = "shop_id", type = IdType.ASSIGN_UUID)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getSharingPhoto() {
        return sharingPhoto;
    }

    public void setSharingPhoto(String sharingPhoto) {
        this.sharingPhoto = sharingPhoto;
    }
    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }
    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }
    public String getShowRecommend() {
        return showRecommend;
    }

    public void setShowRecommend(String showRecommend) {
        this.showRecommend = showRecommend;
    }
    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }
    public String getShopState() {
        return shopState;
    }

    public void setShopState(String shopState) {
        this.shopState = shopState;
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
        return "CanShop{" +
            "shopId=" + shopId +
            ", shopName=" + shopName +
            ", sharingPhoto=" + sharingPhoto +
            ", shopPhone=" + shopPhone +
            ", shopAddress=" + shopAddress +
            ", showRecommend=" + showRecommend +
            ", recommendName=" + recommendName +
            ", shopState=" + shopState +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
