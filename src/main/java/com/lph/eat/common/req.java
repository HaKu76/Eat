package com.lph.eat.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;
/*
* 通用返回结果，服务端响应的数据封装成这个类
*
*/
@Data
public class req<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    // 申请成功返回结果
    public static <T> req<T> success(T object) {
        req<T> r = new req<T>();
        r.data = object;
        r.code = 1;
        return r;
    }
    // 申请失败返回结果
    public static <T> req<T> error(String msg) {
        req r = new req();
        r.msg = msg;
        r.code = 0;
        return r;
    }
    // 动态数据
    public req<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
