package com.lph.eat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
// 过滤器扫描
@ServletComponentScan
// 开启事务
@EnableTransactionManagement
public class EatApplication {

    public static void main(String[] args) {
        SpringApplication.run(EatApplication.class, args);
        log.info("项目启动成功");
    }

}
