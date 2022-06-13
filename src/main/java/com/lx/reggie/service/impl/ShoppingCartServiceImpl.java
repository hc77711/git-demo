package com.lx.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.reggie.common.R;
import com.lx.reggie.mapper.ShoppingCartMapper;
import com.lx.reggie.entity.ShoppingCart;
import com.lx.reggie.service.ShoppingCartService;
import com.lx.reggie.util.UserHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 购物车(ShoppingCart)表服务实现类
 *
 * @author makejava
 * @since 2022-06-13 00:38:32
 */
@Service("shoppingCartService")
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    @Override
    public R<String> add(ShoppingCart shoppingCart) {
        Long userId = UserHolder.getUser();
        // 口味相同,菜品相同 认为是同一个菜品
        ShoppingCart cart = query().eq("user_id", userId)
                .eq(shoppingCart.getDishId() != null, "dish_id", shoppingCart.getDishId())
                .eq(shoppingCart.getSetmealId() != null, "setmeal_id", shoppingCart.getSetmealId())
                .eq(shoppingCart.getDishFlavor() != null, "dish_flavor", shoppingCart.getDishFlavor())
                .one();
        if (Objects.isNull(cart)){
            // 原先不存在
            shoppingCart.setUserId(userId);
            save(shoppingCart);
        }else {
            // 原先存在,做更新操作
            LambdaUpdateWrapper<ShoppingCart> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(ShoppingCart::getId, cart.getId());
            lambdaUpdateWrapper.set(ShoppingCart::getNumber, cart.getNumber() + 1);
            // 获取单个金额信息
            update(lambdaUpdateWrapper);
        }
        return R.success("添加购物车成功");
    }
}

