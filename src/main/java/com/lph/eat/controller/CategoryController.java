package com.lph.eat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lph.eat.common.req;
import com.lph.eat.entity.Category;
import com.lph.eat.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 分类管理控制类
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     * @param request
     * @param category
     * @return
     */
    @PostMapping
    public req<String> save(HttpServletRequest request, @RequestBody Category category) {
        log.info("category:{}", category);
        categoryService.save(category);
        return req.success("新增分类成功");
    }

    /**
     * 分类信息分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public req<Page> page(int page, int pageSize) {
        // 日志接收信息
        log.info("page = {},pageSize = {},name = {}", page, pageSize);
        // 分页信息对象
        Page pageInfo = new Page(page, pageSize);
        // 条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        // 添加排序条件，根据sort进行排序
        queryWrapper.orderByAsc(Category::getSort);
        // 执行查询
        categoryService.page(pageInfo, queryWrapper);

        if (pageInfo.getTotal() > 0) {
            return req.success(pageInfo);
        }
        return req.error("没有查询到数据");
    }

    /**
     * 根据ID删除分类
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public req<String> delete(Long ids) {
        log.info("删除分类，id为：{}", ids);
        // categoryService.removeById(ids);
        // 自定义的删除方法，判断是否关联菜品
        categoryService.remove(ids);
        return req.success("分类信息删除成功");
    }

}
