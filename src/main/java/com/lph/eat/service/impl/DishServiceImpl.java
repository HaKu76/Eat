package com.lph.eat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lph.eat.entity.Dish;
import com.lph.eat.mapper.DishMapper;
import com.lph.eat.service.DishService;
import org.springframework.stereotype.Service;

/**
 * 菜品服务实现类
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
