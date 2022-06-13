package com.lx.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.reggie.common.R;
import com.lx.reggie.dto.DishDto;
import com.lx.reggie.entity.Dish;

import java.util.List;

/**
 * 菜品管理(Dish)表服务接口
 *
 * @author makejava
 * @since 2022-06-11 22:57:13
 */
public interface DishService extends IService<Dish> {

    // 新增菜品,同时插入菜品对应的口味数据, 需要操作两张表 dish dish_flavor
    R<String> saveWithFlavor(DishDto dishDto);

    // 分页查询菜品信息
    R<Page> getPage(int page, int pageSize, String name);


    // 更新菜品信息
    R<String> updateWithFlavor(DishDto dishDto);

    R<List<DishDto>> list(Dish dish);
}

