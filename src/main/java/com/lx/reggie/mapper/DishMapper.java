package com.lx.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.reggie.dto.DishDto;
import com.lx.reggie.entity.Dish;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜品管理(Dish)表数据库访问层
 *
 * @author makejava
 * @since 2022-06-11 22:57:29
 */
public interface DishMapper extends BaseMapper<Dish> {


    List<DishDto> getPage(@Param("page") int page, @Param("pageSize") int pageSize, @Param("name") String name);
}

