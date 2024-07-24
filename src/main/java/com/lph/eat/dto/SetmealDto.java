package com.lph.eat.dto;

import com.lph.eat.entity.Setmeal;
import com.lph.eat.entity.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * 套餐的关联查询实体，包含套餐和分类
 */
@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
