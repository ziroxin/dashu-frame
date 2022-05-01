package com.kg.dashu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// 开启声明式事务
@EnableTransactionManagement
// 扫描Mapper
@MapperScan("com.kg.**.mapper")
// 解决swagger3启动报错，加了该注解 todo 虽已解决，但不明所以，有时间的时候再研究
@EnableWebMvc
// 多模块项目，配置要扫描的包
@SpringBootApplication(scanBasePackages = "com.kg.**")
public class DashuApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashuApplication.class, args);
    }

}
