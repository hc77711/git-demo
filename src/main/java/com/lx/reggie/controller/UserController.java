package com.lx.reggie.controller;



import com.lx.reggie.common.R;
import com.lx.reggie.entity.User;
import com.lx.reggie.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息(User)表控制层
 *
 * @author makejava
 * @since 2022-06-12 23:40:55
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public R<String> login(@RequestBody User user, HttpServletRequest request){
//        userService.save(user);
        request.getSession().setAttribute("user",1536015586017009666L);
        return R.success("注册成功");
    }
}

