package com.kg.core.zuser.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author ziro
 * @since 2022-05-21
 */
@TableName("z_user_role")
@ApiModel(value = "ZUserRole对象", description = "用户角色表")
public class ZUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("user_id")
    @TableId(value = "user_id")
    private String userId;

    @ApiModelProperty("role_id")
    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "ZUserRole{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                "}";
    }
}
