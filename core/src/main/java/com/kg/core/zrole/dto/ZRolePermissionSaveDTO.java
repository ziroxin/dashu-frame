package com.kg.core.zrole.dto;

import com.kg.core.base.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ziro
 * @date 2022-05-25 16:39:01
 */
@Getter
@Setter
@ApiModel(description = "角色权限保存DTO", discriminator = "角色权限保存DTO")
public class ZRolePermissionSaveDTO implements BaseDTO {
    /**
     * 角色id
     */
    @ApiModelProperty("角色Id")
    private String roleId;
    /**
     * 权限列表
     */
    @ApiModelProperty("PermissionId数组")
    private String[] permissionIds;
}
