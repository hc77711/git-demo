package com.lx.reggie.controller;



import com.lx.reggie.common.R;
import com.lx.reggie.entity.Orders;
import com.lx.reggie.service.OrdersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 订单表(Orders)表控制层
 *
 * @author makejava
 * @since 2022-06-13 11:33:34
 */
@RestController
@RequestMapping("order")
public class OrdersController {
    /**
     * 服务对象
     */
    @Resource
    private OrdersService ordersService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        return ordersService.submit(orders);
    }
}

