package com.kg.core.zrole.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author ziro
 * @since 2022-05-21
 */
@TableName("z_role")
@ApiModel(value = "ZRole对象", description = "角色表")
public class ZRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色ID")
    private String roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色描述")
    private String roleDescription;

    @ApiModelProperty("角色顺序")
    private Integer roleOrder;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime updateTime;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
    public Integer getRoleOrder() {
        return roleOrder;
    }

    public void setRoleOrder(Integer roleOrder) {
        this.roleOrder = roleOrder;
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
        return "ZRole{" +
            "roleId=" + roleId +
            ", roleName=" + roleName +
            ", roleDescription=" + roleDescription +
            ", roleOrder=" + roleOrder +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
