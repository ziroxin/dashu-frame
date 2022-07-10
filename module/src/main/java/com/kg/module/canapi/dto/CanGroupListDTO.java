package com.kg.module.canapi.dto;

import com.kg.module.candishes.dto.CanDishesGroupDTO;
import com.kg.module.candishes.entity.CanDishes;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 点餐分组DTO
 *
 * @author ziro
 * @date 2022-07-03 20:57:27
 */
@Getter
@Setter
public class CanGroupListDTO {
    private String groupId;
    private String shopId;
    private String groupName;
    private Integer groupOrder;
    private String groupIcon;
    private List<CanDishesGroupDTO> dishes;
}
