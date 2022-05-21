package com.kg.core.zrole.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @author ziro
 * @since 2022-05-21
 */
@TableName("z_role_permission")
@ApiModel(value = "ZRolePermission对象", description = "角色权限表")
public class ZRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("role_id")
    private String roleId;

    @ApiModelProperty("permission_id")
    private String permissionId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "ZRolePermission{" +
            "roleId=" + roleId +
            ", permissionId=" + permissionId +
        "}";
    }
}
