package com.lph.eat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lph.eat.dto.DishDto;
import com.lph.eat.entity.Dish;

/**
 * 菜品服务类接口
 */
public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);
}
