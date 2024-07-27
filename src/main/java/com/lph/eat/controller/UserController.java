package com.lph.eat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lph.eat.common.req;
import com.lph.eat.entity.User;
import com.lph.eat.service.UserService;
import com.lph.eat.utils.SendEmailUtils;
import com.lph.eat.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 用户信息控制类
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    //自动注入邮件发送工具类
    @Autowired
    private SendEmailUtils sendEmailUtils;

    /**
     * 发送邮箱验证码
     *
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public req<String> sedMsg(@RequestBody User user, HttpSession session) {
        // 获取邮箱
        String email = user.getEmail();
        // 判断邮箱是否为空
        if (email != null) {
            // 生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("生成的验证码为：{}", code);
            //调用邮箱服务，发送邮件
            // sendEmailUtils.sendEmail(email, code);
            // 需要将生成的验证码保存到Session中
            session.setAttribute(email, code);
            return req.success("邮箱验证码发送成功");
        }
        return req.error("邮箱验证码发送失败");
    }

    /**
     * 移动端用户登录
     *
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public req<User> login(@RequestBody Map map, HttpSession session) {
        log.info("map的信息" + map.toString());
        //获取邮箱
        String email = map.get("email").toString();
        // 获取验证码
        String code = map.get("code").toString();
        // 从Session中获取保存的验证码
        Object codeInSession = session.getAttribute(email);
        // 进行验证码的比对
        if (codeInSession != null && codeInSession.equals(code)) {
            // 验证码比对成功，登录成功

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getEmail, email);

            User user = userService.getOne(queryWrapper);
            if (user == null) {
                // 判断当前邮箱对应用户是否存在，若不存在则是新用户自动完成注册
                user = new User();
                user.setEmail(email);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            return req.success(user);
        }
        return req.error("登录失败");
    }
}
