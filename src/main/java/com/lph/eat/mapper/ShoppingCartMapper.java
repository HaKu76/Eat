package com.lph.eat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lph.eat.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * 购物车Mapper接口
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}
