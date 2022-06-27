package com.kg.core.candishes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 菜品分组中间表
 * </p>
 *
 * @author dazui
 * @since 2022-06-25
 */
@TableName("can_dishes_group")
@ApiModel(value = "CanDishesGroup对象", description = "菜品分组中间表")
public class CanDishesGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜品ID")
    @TableId(value = "dishes_id")
    private String dishesId;

    @ApiModelProperty("分组ID")
    private String groupId;

    public String getDishesId() {
        return dishesId;
    }

    public void setDishesId(String dishesId) {
        this.dishesId = dishesId;
    }
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "CanDishesGroup{" +
            "dishesId=" + dishesId +
            ", groupId=" + groupId +
        "}";
    }
}
