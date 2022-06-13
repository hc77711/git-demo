package com.lx.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.reggie.common.R;
import com.lx.reggie.entity.Dish;
import com.lx.reggie.entity.Setmeal;
import com.lx.reggie.mapper.CategoryMapper;
import com.lx.reggie.entity.Category;
import com.lx.reggie.service.CategoryService;
import com.lx.reggie.service.DishService;
import com.lx.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜品及套餐分类(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-06-11 21:35:28
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private DishService dishService;
    @Resource
    private SetmealService setmealService;

    @Override
    public R<String> addCategory(Category category) {
        save(category);
        return R.success("添加成功");
    }

    @Override
    public R<Page> getPage(int page, int pageSize) {
        Page<Category> pageInfo = new Page<>(page, pageSize);

//        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        // 从小到大
//        lambdaQueryWrapper.orderByAsc(Category::getSort);
//
//        page(pageInfo, lambdaQueryWrapper);

        page(pageInfo);
        return R.success(pageInfo);
    }

    @Override
    public R<String> delById(Long ids) {
        // 如果该分类下关联了菜品或者套餐,提示不允许删除
        // 1. 查询当前的分类信息
        Category category = getById(ids);
        // 2. 根据分类是菜品还是套餐去查询
        if (category.getType() == 1){
            // 2.1 菜品分类
            Long count = dishService.query().eq("category_id", ids).count();
            // 2.2 如果菜品或者套餐有关联该分类 id 的,不让删除
            if (count > 0){
                return R.error("该分类下存在菜品,请先删除菜品");
            }
        }else {
            // 2.3 套餐分类
            Long count = setmealService.query().eq("category_id", ids).count();
            if (count > 0){
                return R.error("该分类下存在套餐,请先删除套餐信息");
            }
        }
        // 3. 不存在信息,直接删除即可
        removeById(ids);
        return R.success("删除成功");
    }
}

