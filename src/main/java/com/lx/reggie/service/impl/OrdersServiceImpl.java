package com.lx.reggie.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.reggie.common.R;
import com.lx.reggie.entity.AddressBook;
import com.lx.reggie.entity.OrderDetail;
import com.lx.reggie.entity.ShoppingCart;
import com.lx.reggie.mapper.OrdersMapper;
import com.lx.reggie.entity.Orders;
import com.lx.reggie.service.*;
import com.lx.reggie.util.UserHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单表(Orders)表服务实现类
 *
 * @author makejava
 * @since 2022-06-13 11:33:34
 */
@Service("ordersService")
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Resource
    private ShoppingCartService shoppingCartService;
    @Resource
    private UserService userService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private AddressBookService addressBookService;
    /*
    前端数据 地址 id 支付方式 备注信息
    需要补全的
    订单号 使用雪花算法生成
    订单状态 默认是 1
    下单用户 id userHolder.getUser()
    下单时间 当前时间
    结账时间 默认无
    实收金额 查询该用户在购物车的总金额
    手机号 根据用户查询
     */
    @Transactional
    @Override
    public R<String> submit(Orders orders) {
        Long userId = UserHolder.getUser();
        // 查询地址,看是否存在
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        if (addressBook == null){
            return R.error("地址不能为空");
        }
        List<ShoppingCart> shoppingCarts = shoppingCartService.query().eq("user_id", userId).list();

        if (shoppingCarts == null || shoppingCarts.size() <= 0){
            return R.error("没有购物车数据");
        }
        // 填写用户信息
        long orderId = IdWorker.getId();
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setAddress(addressBook.getDetail());
        orders.setPhone(addressBook.getPhone());
        orders.setUserName(userService.getById(userId).getName());
        orders.setConsignee(addressBook.getConsignee());
        double amount = 0;
        for (ShoppingCart shoppingCart : shoppingCarts){
            amount += shoppingCart.getAmount();
            // 插入订单详情数据
            OrderDetail orderDetail = BeanUtil.copyProperties(shoppingCart, OrderDetail.class);
            orderDetail.setOrderId(orderId);
            orderDetailService.save(orderDetail);
        }
        orders.setAmount(amount);
        // 清空购物车数据
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);
        shoppingCartService.remove(lambdaQueryWrapper);

        // 插入订单数据
        save(orders);
        return R.success("订单创建成功");
    }
}

