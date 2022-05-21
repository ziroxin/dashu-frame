package com.kg.core.zapi.dto;

import com.kg.core.base.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ziro
 * @date 2022-05-21 21:35:09
 */
@Getter
@Setter
public class ApiUserIdDTO implements BaseDTO {
    private String apiPermission;
    private String roleId;
}
