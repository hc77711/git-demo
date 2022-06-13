package com.lx.reggie.controller;



import com.lx.reggie.entity.OrderDetail;
import com.lx.reggie.service.OrderDetailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 订单明细表(OrderDetail)表控制层
 *
 * @author makejava
 * @since 2022-06-13 11:33:34
 */
@RestController
@RequestMapping("orderDetail")
public class OrderDetailController {
    /**
     * 服务对象
     */
    @Resource
    private OrderDetailService orderDetailService;
}

