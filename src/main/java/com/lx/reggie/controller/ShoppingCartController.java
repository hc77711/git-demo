package com.lx.reggie.controller;



import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lx.reggie.common.R;
import com.lx.reggie.entity.ShoppingCart;
import com.lx.reggie.service.ShoppingCartService;
import com.lx.reggie.util.UserHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车(ShoppingCart)表控制层
 *
 * @author makejava
 * @since 2022-06-13 00:38:32
 */
@RestController
@RequestMapping("shoppingCart")
public class ShoppingCartController {
    /**
     * 服务对象
     */
    @Resource
    private ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        return R.success(shoppingCartService.query().eq("user_id", UserHolder.getUser()).orderByAsc("create_time").list());
    }

    @PostMapping("/add")
    public R<String> add(@RequestBody ShoppingCart shoppingCart){
        return shoppingCartService.add(shoppingCart);
    }
}

