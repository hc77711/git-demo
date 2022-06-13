package com.lx.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.reggie.common.R;
import com.lx.reggie.entity.Employee;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 员工信息(Employee)表服务接口
 *
 * @author makejava
 * @since 2022-06-10 23:33:03
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * 登录接口
     * @param username 用户名称
     * @param password 密码
     * @return 是否成功 1 成功
     */
    R login(String username, String password, HttpSession session);

    /**
     * 退出登录
     * @param session
     * @return
     */
    R logout(HttpSession session);

    // 新增员工
    R addEmp(Employee employee, HttpServletRequest request);

    /**
     * 分页查询
     * @param pageNum 第几页
     * @param pageSize 每一页多少条数据
     * @param name 根据名称查询
     * @return 查询到的数据
     */
    R<Page> getPage(int pageNum, int pageSize, String name);

    // 更新状态
    R<String> updateStatus(Employee employee);
}

