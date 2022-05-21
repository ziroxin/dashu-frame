package com.kg.core.zpermission.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 资源API关联表
 * </p>
 *
 * @author ziro
 * @since 2022-05-21
 */
@TableName("z_permission_api")
@ApiModel(value = "ZPermissionApi对象", description = "资源API关联表")
public class ZPermissionApi implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("permission_id")
    private String permissionId;

    @ApiModelProperty("api_id")
    private String apiId;

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    @Override
    public String toString() {
        return "ZPermissionApi{" +
            "permissionId=" + permissionId +
            ", apiId=" + apiId +
        "}";
    }
}
