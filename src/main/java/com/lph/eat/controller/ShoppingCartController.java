package com.lph.eat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lph.eat.common.BaseContext;
import com.lph.eat.common.req;
import com.lph.eat.entity.ShoppingCart;
import com.lph.eat.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 购物车控制类
 */

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public req<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("购物车数据:{}", shoppingCart);
        // 设置用户id，指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        if (dishId != null) {
            // 添加到购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            // 添加到购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        //查询当前菜品和套餐是否在购物车中
        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);

        if (cartServiceOne != null) {
            // 若已经存在，则在原来数量基础上加一
            cartServiceOne.setNumber(cartServiceOne.getNumber() + 1);
            shoppingCartService.updateById(cartServiceOne);
        } else {
            // 若不存在，则添加到购物车，数量默认为一
            shoppingCart.setNumber(1);
            // 设置创建时间
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }
        return req.success(cartServiceOne);
    }


    /**
     * 从购物车中减少商品数量。
     * 当用户通过前端发送请求减少购物车中的菜品或套餐数量时，该方法被调用。
     * 如果减少后数量为零，则从购物车中移除该商品；否则，更新购物车中该商品的数量。
     *
     * @param shoppingCart 购物车对象，包含用户选择的菜品或套餐ID和数量。
     * @return 如果操作成功，返回更新后的购物车项；如果购物车中不存在该商品，则返回错误信息。
     */
    @PostMapping("/sub")
    public req<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
        // 获取当前用户的ID
        Long userId = BaseContext.getCurrentId();

        // 创建查询条件，筛选属于当前用户的购物车项
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);

        // 购物车项，用于更新数据
        ShoppingCart cartToUpdate = null;
        //布尔类型，判断是否包含菜品ID或套餐ID
        boolean isDish = shoppingCart.getDishId() != null;
        boolean isSetMeal = shoppingCart.getSetmealId() != null;

        // 根据请求中的菜品或套餐ID，查询对应的购物车项
        if (isDish || isSetMeal) {
            // 选择菜品或套餐
            queryWrapper.eq(isDish, ShoppingCart::getDishId, shoppingCart.getDishId())
                    .eq(isSetMeal, ShoppingCart::getSetmealId, shoppingCart.getSetmealId());

            // 获取需要更新的购物车项
            cartToUpdate = shoppingCartService.getOne(queryWrapper);

            // 如果找到了对应的购物车项
            if (cartToUpdate != null) {
                // 菜品或套餐数量减一
                cartToUpdate.setNumber(cartToUpdate.getNumber() - 1);

                // 如果菜品数量大于0，更新购物车项
                if (cartToUpdate.getNumber() > 0) {
                    shoppingCartService.updateById(cartToUpdate);
                } else {
                    // 如果菜品数量为0，从购物车中移除该商品
                    shoppingCartService.removeById(cartToUpdate.getId());
                }
                // 返回更新后的购物车项
                return req.success(cartToUpdate);
            }
        }
        // 如果没有找到对应菜品或套餐，返回错误信息
        return req.error("系统繁忙，请稍后再试");
    }


    /**
     * 查看购物车
     *
     * @return
     */
    @GetMapping("/list")
    public req<List<ShoppingCart>> list() {
        log.info("查看购物车");

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        // 根据创建时间升序排序
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        return req.success(list);
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @DeleteMapping("/clean")
    public req<String> clean() {
        // 清空购物车
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);
        return req.success("成功清空购物车");
    }
}
