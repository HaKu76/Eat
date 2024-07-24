package com.lph.eat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lph.eat.common.req;
import com.lph.eat.dto.SetmealDto;
import com.lph.eat.entity.Category;
import com.lph.eat.entity.Setmeal;
import com.lph.eat.service.CategoryService;
import com.lph.eat.service.SetmealDishService;
import com.lph.eat.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐管理控制类
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增套餐
     * 涉及两张表的操作
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public req<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("套餐信息：{}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return req.success("新增套餐成功");
    }

    /**
     * 套餐分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public req<Page> page(int page, int pageSize, String name) {
        // 分页构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        // 初始化用于返回的套餐分页对象
        Page<SetmealDto> dtoPage = new Page<>();
        //条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据name进行like模糊查询
        queryWrapper.like(name != null, Setmeal::getName, name);
        // 添加排序条件，根据更新时间进行降序排列
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        //执行分页查询
        setmealService.page(pageInfo, queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");

        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            //套餐基本信息转换
            BeanUtils.copyProperties(item, setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                //分类名称
                String categoryName = category.getName();
                //设置套餐的分类名称
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        // 设置转换后的套餐详情列表到返回的分页对象
        dtoPage.setRecords(list);

        return req.success(dtoPage);
    }

    /**
     * 启售停售菜品
     *
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public req<String> status(@PathVariable int status, Long[] ids) {

        for (Long id : ids) {
            Setmeal setmeal = setmealService.getById(id);
            setmeal.setStatus(status);
            setmealService.updateById(setmeal);
        }
        return req.success("修改状态成功");
    }

    /**
     * 删除和批量删除套餐
     * 同样是需要操作两张表
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public req<String> delete(@RequestParam List<Long> ids) {
        log.info("ids:{}", ids);
        setmealService.removeWithDish(ids);
        return req.success("套餐数据删除成功");
    }
}
