package com.kg.module.candishes.mapper;

import com.kg.module.candishes.dto.CanDishesGroupDTO;
import com.kg.module.candishes.entity.CanDishes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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

    List<CanDishesGroupDTO> getDishesGroupList(@Param("shopId") String shopId);

}
