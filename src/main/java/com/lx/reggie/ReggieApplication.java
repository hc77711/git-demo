package com.lx.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 小兴
 * @description 启动类
 * @className ReggieApplication
 * @date 2022/6/10 22:41
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan // 扫描 servlet 组件
@EnableTransactionManagement // 事务注解支持
public class ReggieApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class, args);
        log.info("项目启动成功");
    }
}
