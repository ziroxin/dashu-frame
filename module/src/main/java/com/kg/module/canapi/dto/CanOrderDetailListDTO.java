package com.kg.module.canapi.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author yrt
 * @date 2022/7/11 15:05
 */
@Getter
@Setter
public class CanOrderDetailListDTO {

    private String orderId;

    private String openId;

    private String shopId;

    private String tableId;

    private String tableName;

    private Integer diningNumber;

    private LocalDateTime diningTime;

    private Double diningAmount;

    private String invoice;

    private String orderComplete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String orderDetailsId;

    private String dishesName;

    private Integer dishesNumber;

    private Double dishesPrice;

    private Double dishesAmount;

    private String picture;

}
