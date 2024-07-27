package com.lph.eat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lph.eat.entity.AddressBook;
import com.lph.eat.mapper.AddressBookMapper;
import com.lph.eat.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * 地址簿服务实现类
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
