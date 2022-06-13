package com.lx.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.reggie.common.R;
import com.lx.reggie.entity.Employee;
import com.lx.reggie.mapper.EmployeeMapper;
import com.lx.reggie.service.EmployeeService;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 员工信息(Employee)表服务实现类
 *
 * @author makejava
 * @since 2022-06-10 23:33:03
 */
@Service("employeeService")
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Override
    public R login(String username, String password, HttpSession session) {
        // 1. 将页面提交的密码进行 MD5 加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        // 2. 根据页面提交的用户名查询用户
        Employee employee = query().eq("username", username).one();
        if (Objects.isNull(employee)){
            // 如果查询不到用户,这代表用户根本就不存在
            return R.error("用户不存在, 请注册");
        }
        // 3. 密码校验,不一致返回错误
        if (!employee.getPassword().equals(password)){
            return R.error("密码错误了, 是否忘记密码了?");
        }

        // 4. 查看员工状态,看是否被禁用了
        if (employee.getStatus() == 0){
            return R.error("您的账户已经被禁用了, 请联系管理员");
        }

        // 5. 登录成功,存入 session,返回登录结果
        session.setAttribute("employee", employee.getId());
        return R.success(employee);
    }

    @Override
    public R logout(HttpSession session) {
        session.removeAttribute("employee");
        return R.success("成功退出了");
    }

    @Override
    public R addEmp(Employee employee, HttpServletRequest request ) {
        // 设置初始密码,进行加密操作
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        // 填写日志信息
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        Long empId = (Long) request.getSession().getAttribute("employee");
//
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

        save(employee);
        return R.success("新增员工成功");
    }

    @Override
    public R<Page> getPage(int pageNum, int pageSize, String name) {
        // 构造分页构造器
        Page<Employee> pageInfo = new Page<>(pageNum, pageSize);
        // 构造条件构造器
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
//        lambdaQueryWrapper.ne(Employee::getName, "管理员");
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName, name);
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        page(pageInfo, lambdaQueryWrapper);

        // 获取信息,封装返回
        return R.success(pageInfo);
    }

    @Override
    public R<String> updateStatus(Employee employee) {

        employee.setUpdateTime(LocalDateTime.now());
        updateById(employee);
        return R.success(" 操作成功");
    }
}

