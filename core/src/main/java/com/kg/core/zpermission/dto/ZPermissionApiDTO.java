package com.kg.core.zpermission.dto;

import com.kg.core.base.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ziro
 * @date 2022-05-21 15:06:07
 */
@Getter
@Setter
@ApiModel(value = "资源和API关联DTO", description = "资源和API关联关系")
public class ZPermissionApiDTO implements BaseDTO {
    /**
     * 资源id
     */
    @ApiModelProperty("资源Id")
    private String permissionId;
    /**
     * 关联api列表
     */
    @ApiModelProperty("ApiId数组")
    private String[] apiIds;
}
