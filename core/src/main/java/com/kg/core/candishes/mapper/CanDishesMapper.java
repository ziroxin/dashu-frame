package com.kg.core.candishes.mapper;

import com.kg.core.candishes.dto.CanDishesGroupDTO;
import com.kg.core.candishes.entity.CanDishes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜品表 Mapper 接口
 * </p>
 *
 * @author dazui
 * @since 2022-06-25
 */
public interface CanDishesMapper extends BaseMapper<CanDishes> {

    List<CanDishesGroupDTO> getDishesGroupList();

}
