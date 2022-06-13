package com.lx.reggie.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.reggie.common.R;
import com.lx.reggie.dto.DishDto;
import com.lx.reggie.entity.Dish;
import com.lx.reggie.entity.DishFlavor;
import com.lx.reggie.service.DishFlavorService;
import com.lx.reggie.service.DishService;
import com.lx.reggie.service.SetmealService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品管理(Dish)表控制层
 *
 * @author makejava
 * @since 2022-06-11 22:57:13
 */
@RestController
@RequestMapping("dish")
public class DishController {

    @Resource
    private DishService dishService;
    @Resource
    private DishFlavorService dishFlavorService;

    @PostMapping
    public R<String> save(@RequestBody  DishDto dishDto){
        return dishService.saveWithFlavor(dishDto);
    }


    @GetMapping("/page")
    public R<Page> getPage(int page, int pageSize, String  name){
        return dishService.getPage(page, pageSize, name);
    }

    @GetMapping("/{id}")
    public R<DishDto> getByID(@PathVariable String id){
        Dish dish = dishService.getById(id);
        List<DishFlavor> dishFlavorList = dishFlavorService.query().eq("dish_id", dish.getId()).list();
        DishDto dishDto = BeanUtil.copyProperties(dish, DishDto.class);
        dishDto.setFlavors(dishFlavorList);
        return R.success(dishDto);
    };

    @DeleteMapping
    public R<String> delById(String ids){
        String[] split = ids.split(",");
        List<Long> list = Arrays.asList(split).stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        dishService.removeByIds(list);
        return R.success("删除成功");
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(String ids, @PathVariable Long status){
        String[] split = ids.split(",");
        // 修改菜品状态
        LambdaUpdateWrapper<Dish> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.in(Dish::getId, Arrays.asList(split));
        lambdaUpdateWrapper.set(Dish::getStatus, status);
        dishService.update(lambdaUpdateWrapper);
        return R.success(status == 0 ? "停售成功" : "启售成功");
    }

    // 修改菜品
    @PutMapping
    public R<String> update(@RequestBody  DishDto dishDto){
            return dishService.updateWithFlavor(dishDto);
    }

    // 根据分类的 id 去查询商品的信息
    @GetMapping("/list")
    public R<List<DishDto>> listByCategoryId(Dish dish){
        return dishService.list(dish);
    }
}

