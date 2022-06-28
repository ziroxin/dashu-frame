package com.kg.module.candishes.service;

import com.kg.module.candishes.dto.CanDishesGroupDTO;
import com.kg.module.candishes.entity.CanDishes;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜品表 服务类
 * </p>
 *
 * @author dazui
 * @since 2022-06-25
 */
public interface ICanDishesService extends IService<CanDishes> {

    List<CanDishesGroupDTO> getDishesGroupList();

    boolean dishesAdd(CanDishesGroupDTO canDishesGroupDTO);

    boolean dishesUpdate(CanDishesGroupDTO canDishesGroupDTO);
}
