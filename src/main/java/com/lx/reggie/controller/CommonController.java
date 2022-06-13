package com.lx.reggie.controller;

import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import com.lx.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @author 小兴
 * @description TODO 公共的请求接口
 * @className CommonController
 * @date 2022/6/12 1:15
 */

@RestController
@RequestMapping("common")
@Slf4j
public class CommonController {


    @Value("${reggie.path}")
    private String basePath;
    /**
     * 文件上传
     * @param file 需要上传的文件
     * @return 文件在服务器存放的地址
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        // 获取后缀名称
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 使用 uuid 生成文件名称
        String filename = UUID.randomUUID() + suffix;
        File dir = new File(basePath);
        // 判断当前目录是否存在
        if (!dir.exists()){
            // 目录不存在,需要创建
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(basePath + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(filename);
    }

    /**
     * 文件下载
     * @param name 文件名称
     * @return 文件二进制信息
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        // 建立输出流,去本地读取信息
        try {
            FileInputStream fileInputStream = new FileInputStream(basePath + name);
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];

            response.setContentType("image/jpeg");
            int len = 0;
            while ( (len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0 , len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
