package com.lph.eat.common;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 * 以线程为作用域，每个线程保存自己的副本
 * 例如每次获取id时，从当前线程获取是同一个线程同一个id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 获取值
     *
     * @return
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }

    /**
     * 设置值
     *
     * @param id
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }
}
