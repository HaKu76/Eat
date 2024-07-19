package com.lph.eat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lph.eat.entity.Category;

/**
 * 分类服务类接口
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long ids);
}
