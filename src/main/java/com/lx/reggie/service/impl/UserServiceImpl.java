package com.lx.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.reggie.mapper.UserMapper;
import com.lx.reggie.entity.User;
import com.lx.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户信息(User)表服务实现类
 *
 * @author makejava
 * @since 2022-06-12 23:40:55
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

