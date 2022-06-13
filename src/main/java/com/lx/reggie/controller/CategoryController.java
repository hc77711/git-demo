package com.lx.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.reggie.common.R;
import com.lx.reggie.entity.Category;
import com.lx.reggie.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜品及套餐分类(Category)表控制层
 *
 * @author makejava
 * @since 2022-06-11 21:35:28
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @PostMapping
    public R<String> addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        return categoryService.getPage(page, pageSize);
    }

    @DeleteMapping
    public R<String> delById(Long ids){
        return categoryService.delById(ids);
    }

    @PutMapping
    public R<String> updateById(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        // 装配条件
        List<Category> list = categoryService.query()
                .eq(category.getType() != null, "type", category.getType())
                .orderByAsc("sort").orderByDesc("update_time")
                .list();

        return R.success(list);
    }

}

