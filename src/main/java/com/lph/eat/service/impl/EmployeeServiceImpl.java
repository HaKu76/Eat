package com.lph.eat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lph.eat.entity.Employee;
import com.lph.eat.mapper.EmployeeMapper;
import com.lph.eat.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * 员工服务实现类
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
