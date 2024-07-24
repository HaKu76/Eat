package com.lph.eat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lph.eat.entity.SetmealDish;
import com.lph.eat.mapper.SetmealDishMapper;
import com.lph.eat.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 套餐服务实现类
 */
@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
