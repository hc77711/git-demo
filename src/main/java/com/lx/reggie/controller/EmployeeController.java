package com.lx.reggie.controller;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.reggie.common.R;
import com.lx.reggie.entity.Employee;
import com.lx.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 员工信息(Employee)表控制层
 *
 * @author makejava
 * @since 2022-06-10 23:33:01
 */
@RestController
@RequestMapping("employee")
@Slf4j
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R login(@RequestBody Employee employee, HttpSession session){
        return employeeService.login(employee.getUsername(), employee.getPassword(), session);
    }

    @PostMapping("/logout")
    public R logout(HttpSession session){
        return employeeService.logout(session);
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        return R.success(employeeService.getById(id));
    }
    @PostMapping
    public R addEmp(@RequestBody Employee employee, HttpServletRequest request){
        return employeeService.addEmp(employee, request);
    }
    @PutMapping
    public R<String> updateStatus(@RequestBody Employee employee, HttpServletRequest request){
        Long id = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateUser(id);
        return employeeService.updateStatus(employee);
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        return employeeService.getPage(page, pageSize, name);
    }
}

