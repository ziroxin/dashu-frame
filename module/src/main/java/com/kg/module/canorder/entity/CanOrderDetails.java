package com.kg.module.canorder.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 订单详情表
 * </p>
 *
 * @author dazui
 * @since 2022-06-28
 */
@TableName("can_order_details")
@ApiModel(value = "CanOrderDetails对象", description = "订单详情表")
public class CanOrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单详情ID")
    private String orderDetailsId;

    @ApiModelProperty("订单ID")
    @TableId(value = "order_id")
    private String orderId;

    @ApiModelProperty("菜品名称")
    private String dishesName;

    @ApiModelProperty("菜品数量")
    private Integer dishesNumber;

    @ApiModelProperty("菜品价格")
    private Double dishesPrice;

    @ApiModelProperty("金额")
    private Double dishesAmount;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime updateTime;

    public String getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(String orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getDishesName() {
        return dishesName;
    }

    public void setDishesName(String dishesName) {
        this.dishesName = dishesName;
    }
    public Integer getDishesNumber() {
        return dishesNumber;
    }

    public void setDishesNumber(Integer dishesNumber) {
        this.dishesNumber = dishesNumber;
    }
    public Double getDishesPrice() {
        return dishesPrice;
    }

    public void setDishesPrice(Double dishesPrice) {
        this.dishesPrice = dishesPrice;
    }
    public Double getDishesAmount() {
        return dishesAmount;
    }

    public void setDishesAmount(Double dishesAmount) {
        this.dishesAmount = dishesAmount;
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
        return "CanOrderDetails{" +
            "orderDetailsId=" + orderDetailsId +
            ", orderId=" + orderId +
            ", dishesName=" + dishesName +
            ", dishesNumber=" + dishesNumber +
            ", dishesPrice=" + dishesPrice +
            ", dishesAmount=" + dishesAmount +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
