package com.lph.eat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lph.eat.entity.Category;
import com.lph.eat.mapper.CategoryMapper;
import com.lph.eat.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * 分类服务实现类
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
