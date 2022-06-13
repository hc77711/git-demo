package com.lx.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.reggie.mapper.SetmealDishMapper;
import com.lx.reggie.entity.SetmealDish;
import com.lx.reggie.service.SetmealDishService;
import org.springframework.stereotype.Service;

/**
 * 套餐菜品关系(SetmealDish)表服务实现类
 *
 * @author makejava
 * @since 2022-06-12 20:09:43
 */
@Service("setmealDishService")
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {

}

