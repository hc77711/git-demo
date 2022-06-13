package com.lx.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.reggie.common.R;
import com.lx.reggie.entity.ShoppingCart;

/**
 * 购物车(ShoppingCart)表服务接口
 *
 * @author makejava
 * @since 2022-06-13 00:38:32
 */
public interface ShoppingCartService extends IService<ShoppingCart> {

    R<String> add(ShoppingCart shoppingCart);
}

