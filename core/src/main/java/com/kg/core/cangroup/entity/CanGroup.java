package com.kg.core.cangroup.entity;

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
 * 菜品分组表
 * </p>
 *
 * @author dazui
 * @since 2022-06-24
 */
@TableName("can_group")
@ApiModel(value = "CanGroup对象", description = "菜品分组表")
public class CanGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分组ID")
    @TableId(value = "group_id", type = IdType.ASSIGN_UUID)
    private String groupId;

    @ApiModelProperty("店铺ID")
    private String shopId;

    @ApiModelProperty("分组名称")
    private String groupName;

    @ApiModelProperty("分组显示顺序")
    private Integer groupOrder;

    @ApiModelProperty("图标")
    private String groupIcon;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("最后更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public Integer getGroupOrder() {
        return groupOrder;
    }

    public void setGroupOrder(Integer groupOrder) {
        this.groupOrder = groupOrder;
    }
    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
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
        return "CanGroup{" +
            "groupId=" + groupId +
            ", shopId=" + shopId +
            ", groupName=" + groupName +
            ", groupOrder=" + groupOrder +
            ", groupIcon=" + groupIcon +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
