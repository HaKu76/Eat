package com.lph.eat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lph.eat.dto.DishDto;
import com.lph.eat.entity.Dish;

/**
 * 菜品服务类接口
 */
public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    void saveWithFlavor(DishDto dishDto);

    // 根据id查询菜品信息和对应的口味信息
    DishDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应的口味信息
    void updateWithFlavor(DishDto dishDto);

    // 删除菜品信息，同时删除对应的口味信息
    void remove(Long id);
}
