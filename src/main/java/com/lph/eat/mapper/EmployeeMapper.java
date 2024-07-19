package com.lph.eat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lph.eat.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

// 员工Mapper接口
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
