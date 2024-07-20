package com.lph.eat.controller;

import com.lph.eat.common.req;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传和下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    // 通用路径
    @Value("${Eat.path}")
    private String basePath;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    // 参数名字必须和组件的name属性一致
    public req<String> upload(MultipartFile file) {
        // file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        log.info(file.toString());

        // 原始文件名，例如abc.jpg
        String originalFilename = file.getOriginalFilename();

        // 后缀名截取，例如jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;
        // 创建目录对象
        File dir = new File(basePath);
        // 判断目录是否存在，不存在则创建
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            // 临时文件转存到指定位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return req.success(fileName);
    }

    /**
     * 文件下载，通过给定的文件名，从指定路径读取文件，并将其发送到客户端。
     *
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
            // 创建文件输入流，用于从文件系统中读取文件内容。
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            // 创建 Servlet 输出流，用于将文件内容写入 HTTP 响应，通过输出流将文件写回浏览器，在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();

            // 设置响应内容类型为 JPEG 图像，确保浏览器正确解析和显示文件。
            response.setContentType("image/jpeg");

            // 读取文件内容并写入输出流，每次读取 1024 字节。
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

            // 关闭输入输出流，释放资源。
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            // 打印异常堆栈跟踪，用于问题排查。
            e.printStackTrace();
        }
    }
}
