package com.lph.eat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lph.eat.common.req;
import com.lph.eat.entity.Employee;
import com.lph.eat.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public req<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        // 1.获取页面提交密码进行md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        // 2.根据页面提交的用户名查询数据库
        // mybatis-plus条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 查询条件
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        // 执行查询获取结果
        Employee emp = employeeService.getOne(queryWrapper);
        // 3.查询失败返回错误信息
        if (emp == null){
            return req.error("登录失败");
        }
        // 4.密码比对
        if (!emp.getPassword().equals(password)){
            return req.error("登录失败");
        }
        // 5.查看员工状态是否禁用
        if (emp.getStatus() == 0){
            return req.error("账号已禁用");
        }
        // 6.登录成功，id存入session
        request.getSession().setAttribute("employee",emp.getId());
        return req.success(emp);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public req<String> logout(HttpServletRequest request){
        // 清除session
        request.getSession().removeAttribute("employee");
        return req.success("退出成功");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public req<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工，员工信息：{}",employee.toString());
        // 设置初始密码123456，进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // 设置创建时间、更新时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        // 获得当前用户id
        Long empId = (Long) request.getSession().getAttribute("employee");

        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);
        // 调用mybatis-plus的save方法
        employeeService.save(employee);
        // 发送新增成功请求，才能跳转页面
        return req.success("新增员工成功");
    }
}
