package com.lx.reggie.entity;

import java.util.Date;
import lombok.Data;

/**
 * 购物车(ShoppingCart)表实体类
 *
 * @author makejava
 * @since 2022-06-13 00:38:32
 */
@SuppressWarnings("serial")
@Data
public class ShoppingCart {
    //主键
    private Long id;
    //名称
    private String name;
    //图片
    private String image;
    //主键
    private Long userId;
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
    //创建时间
    private Date createTime;
}

