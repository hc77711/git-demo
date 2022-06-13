package com.lx.reggie.entity;

import lombok.Data;

/**
 * 订单明细表(OrderDetail)表实体类
 *
 * @author makejava
 * @since 2022-06-13 11:33:34
 */
@SuppressWarnings("serial")
@Data
public class OrderDetail {
    //主键
    private Long id;
    //名字
    private String name;
    //图片
    private String image;
    //订单id
    private Long orderId;
    //菜品id
    private Long dishId;
    //套餐id
    private Long setmealId;
    //口味
    private String dishFlavor;
    //数量
    private Integer number;
    //金额
    private Double amount;
}

