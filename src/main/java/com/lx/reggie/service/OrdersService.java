package com.lx.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.reggie.common.R;
import com.lx.reggie.entity.Orders;

/**
 * 订单表(Orders)表服务接口
 *
 * @author makejava
 * @since 2022-06-13 11:33:34
 */
public interface OrdersService extends IService<Orders> {

    R<String> submit(Orders orders);
}

