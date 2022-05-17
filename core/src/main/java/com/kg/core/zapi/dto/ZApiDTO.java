package com.kg.core.zapi.dto;

import com.kg.core.base.dto.BaseDTO;
import com.kg.core.zapi.entity.ZApi;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ziro
 * @date 2022-05-17 21:20:08
 */
@Getter
@Setter
public class ZApiDTO implements BaseDTO {
    @ApiModelProperty("API分组ID")
    private String apiGroupId;

    @ApiModelProperty("API分组名称")
    private String groupName;

    @ApiModelProperty("API列表")
    List<ZApi> apiList;
}
