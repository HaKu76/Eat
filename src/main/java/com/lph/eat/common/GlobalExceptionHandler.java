package com.lph.eat.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
// 关键点1，底层是代理，选择监控控制器
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理方法，处理SQL异常
     *
     * @return
     */
    // 关键点2，选择处理的异常类型
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public req<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        // 异常信息，日志提示
        log.error(ex.getMessage());
        // 判断异常的关键词是否存在
        if (ex.getMessage().contains("Duplicate entry")) {
            // 空格切分字符串数组
            String[] split = ex.getMessage().split(" ");
            // 索引username是第三个
            String msg = split[2] + "已存在";
            // 返回对应错误信息
            return req.error(msg);
        }
        // 其他错误
        return req.error("未知错误");
    }

    /**
     * 异常处理方法，处理自定义异常
     *
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public req<String> exceptionHandler(CustomException ex) {
        // 异常信息，日志提示
        log.error(ex.getMessage());

        return req.error(ex.getMessage());
    }
}
