package com.lx.reggie.entity;


import lombok.Data;

/**
 * 用户信息(User)表实体类
 *
 * @author makejava
 * @since 2022-06-12 23:40:55
 */
@SuppressWarnings("serial")
@Data
public class User {
    //主键
    private Long id;
    //姓名
    private String name;
    //手机号
    private String phone;
    //性别
    private String sex;
    //身份证号
    private String idNumber;
    //头像
    private String avatar;
    //状态 0:禁用，1:正常
    private Integer status;
}

