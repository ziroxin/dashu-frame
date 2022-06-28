package com.kg.module.canorder.entity;

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
 * 订单表
 * </p>
 *
 * @author dazui
 * @since 2022-06-27
 */
@TableName("can_order")
@ApiModel(value = "CanOrder对象", description = "订单表")
public class CanOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单ID")
    @TableId(value = "order_id", type = IdType.ASSIGN_UUID)
    private String orderId;

    @ApiModelProperty("微信openID")
    private String openId;

    @ApiModelProperty("店铺ID")
    private String shopId;

    @ApiModelProperty("餐桌ID")
    private String tableId;

    @ApiModelProperty("餐桌名称")
    private String tableName;

    @ApiModelProperty("就餐人数")
    private Integer diningNumber;

    @ApiModelProperty("就餐时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime diningTime;

    @ApiModelProperty("就餐金额")
    private Double diningAmount;

    @ApiModelProperty("是否开发票（0是，1否）")
    private String invoice;

    @ApiModelProperty("订单是否完成（0是，1否）")
    private String orderComplete;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("最后更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public Integer getDiningNumber() {
        return diningNumber;
    }

    public void setDiningNumber(Integer diningNumber) {
        this.diningNumber = diningNumber;
    }
    public LocalDateTime getDiningTime() {
        return diningTime;
    }

    public void setDiningTime(LocalDateTime diningTime) {
        this.diningTime = diningTime;
    }
    public Double getDiningAmount() {
        return diningAmount;
    }

    public void setDiningAmount(Double diningAmount) {
        this.diningAmount = diningAmount;
    }
    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }
    public String getOrderComplete() {
        return orderComplete;
    }

    public void setOrderComplete(String orderComplete) {
        this.orderComplete = orderComplete;
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
        return "CanOrder{" +
            "orderId=" + orderId +
            ", openId=" + openId +
            ", shopId=" + shopId +
            ", tableId=" + tableId +
            ", tableName=" + tableName +
            ", diningNumber=" + diningNumber +
            ", diningTime=" + diningTime +
            ", diningAmount=" + diningAmount +
            ", invoice=" + invoice +
            ", orderComplete=" + orderComplete +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
