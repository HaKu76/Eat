package com.lph.eat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lph.eat.entity.User;
import org.apache.ibatis.annotations.Mapper;

// 用户信息Mapper接口
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
