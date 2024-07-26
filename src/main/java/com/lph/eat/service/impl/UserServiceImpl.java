package com.lph.eat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lph.eat.entity.User;
import com.lph.eat.mapper.UserMapper;
import com.lph.eat.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户信息服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
