package com.lph.eat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lph.eat.dto.SetmealDto;
import com.lph.eat.entity.Setmeal;

import java.util.List;

/**
 * 套餐服务类接口
 */
public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，保存套餐和套餐菜品关联数据
     *
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时删除套餐和菜品的关联数据
     *
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
