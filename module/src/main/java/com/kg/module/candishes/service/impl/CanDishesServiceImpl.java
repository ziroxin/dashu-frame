package com.kg.module.candishes.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kg.component.utils.GuidUtils;
import com.kg.module.candishes.dto.CanDishesGroupDTO;
import com.kg.module.candishes.entity.CanDishes;
import com.kg.module.candishes.entity.CanDishesGroup;
import com.kg.module.candishes.mapper.CanDishesMapper;
import com.kg.module.candishes.service.ICanDishesGroupService;
import com.kg.module.candishes.service.ICanDishesService;
import com.kg.module.cangroup.service.ICanGroupService;
import com.kg.module.canshop.entity.CanUserShop;
import com.kg.core.security.util.CurrentUserUtils;
import com.kg.core.zuser.entity.ZUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 菜品表 服务实现类
 * </p>
 *
 * @author dazui
 * @since 2022-06-25
 */
@Service
public class CanDishesServiceImpl extends ServiceImpl<CanDishesMapper, CanDishes> implements ICanDishesService {

    @Autowired
    private CanDishesMapper canDishesMapper;

    @Autowired
    private ICanGroupService canGroupService;

    @Autowired
    private ICanDishesGroupService canDishesGroupService;

    @Override
    public List<CanDishesGroupDTO> getDishesGroupList() {
        return canDishesMapper.getDishesGroupList();
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean dishesAdd(CanDishesGroupDTO canDishesGroupDTO) {
        ZUser user = CurrentUserUtils.getCurrentUser();
        CanUserShop usershop = canGroupService.getUserShop(user.getUserId());

        CanDishes canDishes = new CanDishes();
        BeanUtils.copyProperties(canDishesGroupDTO, canDishes);
        canDishes.setShopId(usershop.getShopId());
        canDishes.setDishesId(GuidUtils.getUuid());
        canDishes.setCreateTime(LocalDateTime.now());

        CanDishesGroup canDishesGroup = new CanDishesGroup();
        canDishesGroup.setDishesId(canDishes.getDishesId());
        canDishesGroup.setGroupId(canDishesGroupDTO.getGroupId());

        boolean s1 = save(canDishes);
        boolean s2 = canDishesGroupService.save(canDishesGroup);

        return (!s1 && !s2);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean dishesUpdate(CanDishesGroupDTO canDishesGroupDTO) {
        CanDishes canDishes = new CanDishes();
        BeanUtils.copyProperties(canDishesGroupDTO, canDishes);
        canDishes.setUpdateTime(LocalDateTime.now());
        boolean s1 = updateById(canDishes);

        boolean s2 = canDishesGroupService.removeById(canDishes.getDishesId());

        CanDishesGroup canDishesGroup = new CanDishesGroup();
        canDishesGroup.setDishesId(canDishes.getDishesId());
        canDishesGroup.setGroupId(canDishesGroupDTO.getGroupId());
        boolean s3 = canDishesGroupService.save(canDishesGroup);

        return (!s1 && !s2 && !s3);
    }
}
