package com.lx.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.reggie.mapper.OrderDetailMapper;
import com.lx.reggie.entity.OrderDetail;
import com.lx.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * 订单明细表(OrderDetail)表服务实现类
 *
 * @author makejava
 * @since 2022-06-13 11:33:34
 */
@Service("orderDetailService")
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}

