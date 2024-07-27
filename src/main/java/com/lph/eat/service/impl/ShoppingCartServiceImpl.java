package com.lph.eat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lph.eat.entity.ShoppingCart;
import com.lph.eat.mapper.ShoppingCartMapper;
import com.lph.eat.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * 购物车服务实现类
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
