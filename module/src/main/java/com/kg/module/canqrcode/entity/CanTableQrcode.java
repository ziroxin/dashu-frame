package com.kg.module.canqrcode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 餐座二维码信息表
 * </p>
 *
 * @author ziro
 * @since 2022-07-28
 */
@TableName("can_table_qrcode")
@ApiModel(value = "CanTableQrcode对象", description = "餐座二维码信息表")
public class CanTableQrcode implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("微信小程序二维码参数id")
    @TableId(value = "qrcode_id", type = IdType.ASSIGN_UUID)
    private String qrcodeId;

    @ApiModelProperty("餐桌id")
    private String tableId;

    @ApiModelProperty("店铺id")
    private String shopId;

    @ApiModelProperty("二维码文件名")
    private String qrcodeFileName;

    @ApiModelProperty("二维码保存路径")
    private String qrcodeFilePath;

    @ApiModelProperty("添加时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    public String getQrcodeId() {
        return qrcodeId;
    }

    public void setQrcodeId(String qrcodeId) {
        this.qrcodeId = qrcodeId;
    }

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

    public String getQrcodeFileName() {
        return qrcodeFileName;
    }

    public void setQrcodeFileName(String qrcodeFileName) {
        this.qrcodeFileName = qrcodeFileName;
    }

    public String getQrcodeFilePath() {
        return qrcodeFilePath;
    }

    public void setQrcodeFilePath(String qrcodeFilePath) {
        this.qrcodeFilePath = qrcodeFilePath;
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
        return "CanTableQrcode{" +
                "qrcodeId=" + qrcodeId +
                ", tableId=" + tableId +
                ", shopId=" + shopId +
                ", qrcodeFileName=" + qrcodeFileName +
                ", qrcodeFilePath=" + qrcodeFilePath +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
