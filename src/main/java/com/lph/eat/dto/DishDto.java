package com.lph.eat.dto;

import com.lph.eat.entity.Dish;
import com.lph.eat.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 关联查询菜品和口味
 */
@Data
public class DishDto extends Dish {
    // 菜品对应的口味数据
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
