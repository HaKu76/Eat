package com.lph.eat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lph.eat.common.req;
import com.lph.eat.entity.Employee;
import com.lph.eat.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public req<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 1.获取页面提交密码进行md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        // 2.根据页面提交的用户名查询数据库
        // mybatis-plus条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 查询条件
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        // 执行查询获取结果
        Employee emp = employeeService.getOne(queryWrapper);
        // 3.查询失败返回错误信息
        if (emp == null) {
            return req.error("登录失败");
        }
        // 4.密码比对
        if (!emp.getPassword().equals(password)) {
            return req.error("登录失败");
        }
        // 5.查看员工状态是否禁用
        if (emp.getStatus() == 0) {
            return req.error("账号已禁用");
        }
        // 6.登录成功，id存入session
        request.getSession().setAttribute("employee", emp.getId());
        return req.success(emp);
    }

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public req<String> logout(HttpServletRequest request) {
        // 清除session
        request.getSession().removeAttribute("employee");
        return req.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public req<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工，员工信息：{}", employee.toString());
        // 设置初始密码123456，进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // 设置创建时间、更新时间
        // employee.setCreateTime(LocalDateTime.now());
        // employee.setUpdateTime(LocalDateTime.now());
        // 获得当前用户id
        // Long empId = (Long) request.getSession().getAttribute("employee");

        // employee.setCreateUser(empId);
        // employee.setUpdateUser(empId);
        // 调用mybatis-plus的save方法
        employeeService.save(employee);
        // 发送新增成功请求，才能跳转页面
        return req.success("新增员工成功");
    }

    /**
     * 员工信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public req<Page> page(int page, int pageSize, String name) {
        // 日志接收信息
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);
        // 分页信息对象
        Page pageInfo = new Page(page, pageSize);
        // 条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 判断搜索name,过滤条件
        // 相似度查询
        queryWrapper.like(name != null, Employee::getName, name);
        // 添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        // 执行查询
        employeeService.page(pageInfo, queryWrapper);

        if (pageInfo.getTotal() > 0) {
            return req.success(pageInfo);
        }
        return req.error("没有查询到数据");
    }

    /**
     * 修改员工信息
     *
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public req<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());
        // 通过前端请求获取员工id
        // Long empId = (Long) request.getSession().getAttribute("employee");
        // 设置更新事件
        // employee.setUpdateTime(LocalDateTime.now());
        // 设置更新用户id
        // employee.setUpdateUser(empId);
        // 调用mybatis-plus的update方法
        employeeService.updateById(employee);
        return req.success("员工信息修改成功");
    }

    /**
     * 根据id查询员工信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public req<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工信息");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return req.success(employee);
        }
        return req.error("没有查询到对应员工信息");
    }
}
