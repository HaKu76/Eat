package com.lph.eat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lph.eat.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * 地址簿Mapper接口
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}
