package com.lx.reggie.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.reggie.common.R;
import com.lx.reggie.dto.DishDto;
import com.lx.reggie.entity.Dish;
import com.lx.reggie.entity.DishFlavor;
import com.lx.reggie.mapper.DishMapper;
import com.lx.reggie.service.DishFlavorService;
import com.lx.reggie.service.DishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品管理(Dish)表服务实现类
 *
 * @author makejava
 * @since 2022-06-11 22:57:13
 */
@Service("dishService")
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Resource
    private DishFlavorService dishFlavorService;
//    @Resource
//    private CategoryService categoryService;


    @Transactional
    @Override
    public R<String> saveWithFlavor(DishDto dishDto) {
        // 根据 dish_id 来添加数据,所以需要先获取添加完成的 dish_id
        // 1. 添加 dish
        Dish dish = BeanUtil.copyProperties(dishDto, Dish.class);
        save(dish);
        // 2. 在 save 的时候会自动将生成的 id 赋给 dish对象
        // 循环插入 flavor 即可
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dish.getId());
        }
        dishFlavorService.saveBatch(flavors);
        return R.success("插入成功");
    }

    @Override
    public R<Page> getPage(int page, int pageSize, String name) {
        List<DishDto> dtos = baseMapper.getPage((page - 1) * pageSize, pageSize, StrUtil.isNotBlank(name) ? "%" + name + "%" : null);
        Page<DishDto> dtoPage = new Page<>();


        dtoPage.setRecords(dtos);
        dtoPage.setTotal(count());
        return R.success(dtoPage);
    }

    @Transactional
    @Override
    public R<String> updateWithFlavor(DishDto dishDto) {
        // 根据 dish_id 来添加数据,所以需要先获取添加完成的 dish_id
        // 1. 添加 dish
        Dish dish = BeanUtil.copyProperties(dishDto, Dish.class);
        updateById(dish);
        // 先删除该菜品对应的口味,在重新保存
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // DishFlavor::getDishId 调用该方法获取值,和 dish.getId 做比较,如果成功,满足条件
        lambdaQueryWrapper.eq(DishFlavor::getDishId, dish.getId());
        dishFlavorService.remove(lambdaQueryWrapper);

        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dish.getId());
        }
        dishFlavorService.saveBatch(flavors);
        return R.success("更新成功");
    }

    @Override
    public R<List<DishDto>> list(Dish dish) {
        List<Dish> dishList = query().eq("status", 1)
                .eq(dish.getCategoryId() != null, "category_id", dish.getCategoryId())
                .orderByAsc("sort").list();

        List<DishDto> list = dishList.stream()
                .map(dish1 -> {
                    DishDto dishDto = BeanUtil.copyProperties(dish1, DishDto.class);
                    dishDto.setFlavors(dishFlavorService.query().eq("dish_id", dish1.getId()).list());
                    return dishDto;
                }).collect(Collectors.toList());
        return R.success(list);
    }

}

