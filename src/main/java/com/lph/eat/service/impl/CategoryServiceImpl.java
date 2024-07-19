package com.lph.eat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lph.eat.common.CustomException;
import com.lph.eat.entity.Category;
import com.lph.eat.entity.Dish;
import com.lph.eat.entity.Setmeal;
import com.lph.eat.mapper.CategoryMapper;
import com.lph.eat.service.CategoryService;
import com.lph.eat.service.DishService;
import com.lph.eat.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分类服务实现类
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类，删除之前需要进行判断
     *
     * @param ids
     */
    @Override
    public void remove(Long ids) {
        //构建查询条件
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, ids);
        //查询该分类是否关联了菜品，如果已经关联，则抛出一个业务异常
        if (dishService.count(dishLambdaQueryWrapper) > 0) {
            // 已经关联，抛出一个业务异常
            throw new CustomException("该分类关联了菜品，不能删除");
        }

        //构建查询条件
        LambdaQueryWrapper<Setmeal> SetmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据分类id进行查询
        SetmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, ids);
        //查询该分类是否关联了套餐，如果已经关联，则抛出一个业务异常
        if (setmealService.count(SetmealLambdaQueryWrapper) > 0) {
            // 已经关联，抛出一个业务异常
            throw new CustomException("该分类关联了套餐，不能删除");
        }

        //正常删除分类
        super.removeById(ids);
    }
}
