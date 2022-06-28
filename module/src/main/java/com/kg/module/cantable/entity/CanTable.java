package com.kg.module.cantable.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 餐桌表
 * </p>
 *
 * @author dazui
 * @since 2022-06-27
 */
@TableName("can_table")
@ApiModel(value = "CanTable对象", description = "餐桌表")
public class CanTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("餐桌ID")
    @TableId(value = "table_id", type = IdType.ASSIGN_UUID)
    private String tableId;

    @ApiModelProperty("店铺ID")
    private String shopId;

    @ApiModelProperty("餐桌名称")
    private String tableName;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime updateTime;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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
        return "CanTable{" +
            "tableId=" + tableId +
            ", shopId=" + shopId +
            ", tableName=" + tableName +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
