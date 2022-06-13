package com.lx.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.reggie.mapper.DishFlavorMapper;
import com.lx.reggie.entity.DishFlavor;
import com.lx.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * 菜品口味关系表(DishFlavor)表服务实现类
 *
 * @author makejava
 * @since 2022-06-12 11:24:02
 */
@Service("dishFlavorService")
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {

}

