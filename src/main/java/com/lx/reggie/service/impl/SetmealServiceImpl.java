package com.lx.reggie.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.reggie.common.R;
import com.lx.reggie.dto.SetmealDto;
import com.lx.reggie.entity.Category;
import com.lx.reggie.entity.Setmeal;
import com.lx.reggie.entity.SetmealDish;
import com.lx.reggie.mapper.SetmealMapper;
import com.lx.reggie.service.CategoryService;
import com.lx.reggie.service.SetmealDishService;
import com.lx.reggie.service.SetmealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐(Setmeal)表服务实现类
 *
 * @author makejava
 * @since 2022-06-11 22:57:13
 */
@Service("setmealService")
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Resource
    private SetmealDishService setmealDishService;
    @Resource
    private CategoryService categoryService;
    @Transactional
    @Override
    public R<String> saveSetmel(SetmealDto setmealDto) {
        // 1. 保存套餐相关信息
        Setmeal setmeal = BeanUtil.copyProperties(setmealDto, Setmeal.class);
        save(setmeal);
        // 2. 保存套餐和菜品的相关信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmeal.getId());
        }
        setmealDishService.saveBatch(setmealDishes);
        return R.success("新增套餐成功");
    }

    @Override
    public R<Page<SetmealDto>> page(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StrUtil.isNotBlank(name), Setmeal::getName, name);
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);

        page(pageInfo, lambdaQueryWrapper);

        // 拷贝的时候,只需要 page 信息,不需要拷贝 records
        BeanUtil.copyProperties(pageInfo, dtoPage, "records");

        List<Setmeal> records = pageInfo.getRecords();
        // 查询分类信息,然后封装一个存放 SetmealDto 的 list 集合,然后给到 pageInfo 即可
        List<SetmealDto> setmealDtos = BeanUtil.copyToList(records, SetmealDto.class);
        for (SetmealDto setmealDto : setmealDtos) {
            Category category = categoryService.getById(setmealDto.getCategoryId());
            setmealDto.setCategoryName(category.getName());
        }
        dtoPage.setRecords(setmealDtos);
        return R.success(dtoPage);
    }

    @Transactional
    @Override
    public R<List<String>> del(String ids) {
        String[] split = ids.split(",");
        List<String> info = new ArrayList<>();
        // 删除套餐信息
        List<Long> list = Arrays.stream(split)
                .map(Long::valueOf)
                .collect(Collectors.toList());
        List<Setmeal> listByIds = listByIds(list);
        for (Setmeal setmeal : listByIds) {
            if (setmeal.getStatus() != 1){
                // 在停售状态下,可以删除
                removeBatchByIds(list);
                // 删除套餐对应的
                for (Long id : list) {
                    LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                    lambdaQueryWrapper.eq(SetmealDish::getSetmealId, id);
                    setmealDishService.remove(lambdaQueryWrapper);
                }
                continue;
            }
            info.add(setmeal.getId() + "删除失败,该套餐未被停售");
        }
        return R.success(info);
    }

    // 更换状态
    @Override
    public R<String> updateStatus(int state, String ids) {
        String[] split = ids.split(",");
        List<Long> list = Arrays.stream(split)
                .map(Long::valueOf)
                .collect(Collectors.toList());

        LambdaUpdateWrapper<Setmeal> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.in(Setmeal::getId, list);
        lambdaUpdateWrapper.set(Setmeal::getStatus, state);

        update(lambdaUpdateWrapper);
        return R.success(state == 0 ? "停售成功" : "启售成功");
    }
}

