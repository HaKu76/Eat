package com.lph.eat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lph.eat.entity.Setmeal;
import com.lph.eat.mapper.SetmealMapper;
import com.lph.eat.service.SetmealService;
import org.springframework.stereotype.Service;

/**
 * 套餐服务实现类
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
