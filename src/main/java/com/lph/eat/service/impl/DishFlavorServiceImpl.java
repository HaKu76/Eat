package com.lph.eat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lph.eat.entity.DishFlavor;
import com.lph.eat.mapper.DishFlavorMapper;
import com.lph.eat.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * 菜品口味服务实现类
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
