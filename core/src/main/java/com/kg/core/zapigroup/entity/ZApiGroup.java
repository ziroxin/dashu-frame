package com.kg.core.zapigroup.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * API分组信息表
 * </p>
 *
 * @author ziro
 * @since 2022-05-17
 */
@TableName("z_api_group")
@ApiModel(value = "ZApiGroup对象", description = "API分组信息表")
public class ZApiGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("API分组ID")
    private String apiGroupId;

    @ApiModelProperty("分组名称")
    private String groupName;

    @ApiModelProperty("分组顺序")
    private Integer groupOrder;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime updateTime;

    public String getApiGroupId() {
        return apiGroupId;
    }

    public void setApiGroupId(String apiGroupId) {
        this.apiGroupId = apiGroupId;
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
        return "ZApiGroup{" +
            "apiGroupId=" + apiGroupId +
            ", groupName=" + groupName +
            ", groupOrder=" + groupOrder +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
