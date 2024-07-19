package com.lph.eat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lph.eat.entity.Category;
import org.apache.ibatis.annotations.Mapper;

// 分类Mapper接口
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
