package com.kg.module.canorder.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kg.core.base.dto.BaseDTO;
import com.kg.module.canorder.entity.CanOrderDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ziro
 * @date 2022-07-15 17:24:05
 */
@Getter
@Setter
public class CanOrderDTO implements BaseDTO {
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

    /**
     * 订单详情信息
     */
    List<CanOrderDetails> canOrderDetails;
}
