package com.lx.reggie.controller;



import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.reggie.common.R;
import com.lx.reggie.dto.SetmealDto;
import com.lx.reggie.entity.Setmeal;
import com.lx.reggie.entity.SetmealDish;
import com.lx.reggie.service.SetmealDishService;
import com.lx.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐(Setmeal)表控制层
 *
 * @author makejava
 * @since 2022-06-11 22:57:13
 */
@RestController
@RequestMapping("setmeal")
@Slf4j
public class SetmealController {

    @Resource
    private SetmealService setmealService;


    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        return setmealService.saveSetmel(setmealDto);
    }

    @GetMapping("/page")
    public R<Page<SetmealDto>> list(int page, int pageSize, String name){
        return setmealService.page(page, pageSize, name);
    }

    @DeleteMapping
    public R<List<String>> del(String ids){
        return setmealService.del(ids);
    }

    @PostMapping("/status/{state}")
    public R<String> updateStatus(String ids, @PathVariable int state){
        return setmealService.updateStatus(state, ids);
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Long categoryId, int status){
        return R.success(setmealService.query().eq("category_id", categoryId).eq("status", status).list());
    }
}

