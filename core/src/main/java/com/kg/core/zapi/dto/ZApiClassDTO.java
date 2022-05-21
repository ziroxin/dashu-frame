package com.kg.core.zapi.dto;

import com.kg.core.base.dto.BaseDTO;
import com.kg.core.zapi.entity.ZApi;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ziro
 * @date 2022-05-20 22:50:57
 */
@Getter
@Setter
public class ZApiClassDTO implements BaseDTO {

    @ApiModelProperty("API的Controller名称")
    private String className;

    @ApiModelProperty("API列表")
    List<ZApi> apiList;
}
