package com.zicheng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 子诚
 * Description：启动类
 * 时间：2020/3/6 6:17
 */
@SpringBootApplication
@MapperScan("com.zicheng.mapper")
public class SpringBootShiroApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootShiroApplication.class);
    }
}
