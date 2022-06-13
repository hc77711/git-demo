package com.lx.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.reggie.common.R;
import com.lx.reggie.entity.Category;

/**
 * 菜品及套餐分类(Category)表服务接口
 *
 * @author makejava
 * @since 2022-06-11 21:35:28
 */
public interface CategoryService extends IService<Category> {

    // 新增菜品分类
    R<String> addCategory(Category category);

    // 获取分页信息
    R<Page> getPage(int page, int pageSize);

    R<String> delById(Long ids);
}

