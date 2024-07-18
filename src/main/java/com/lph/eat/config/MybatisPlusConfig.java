package com.lph.eat.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置分页插件
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 配置MybatisPlus拦截器。
     * 该拦截器主要用于增强MybatisPlus的功能，例如分页插件的处理。
     *
     * @return MybatisPlusInterceptor 实例，配置了分页插件以支持MySQL数据库的分页查询。
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 创建MybatisPlusInterceptor实例
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加内建的分页插件，指定适用于MySQL数据库
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 返回配置好的拦截器实例
        return interceptor;
    }
}
