package com.kg;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

/**
 * 基于Mybatis plus的代码生成器
 *
 * @author ziro
 * @date 2022/4/30 14:29
 * @see <a href="https://www.mybatis-plus.com/guide/generator-new.html#%E5%BF%AB%E9%80%9F%E5%85%A5%E9%97%A8">代码生成器入门</a>
 */
@SpringBootTest
public class MybatisPlusGenerator {

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUserName;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Test
    public void generator() {
        // 指定输出目录==========改成自己的目录
        String basePath = "E:\\IdeaProjects\\fwwbsyb\\dashu-frame\\core\\src\\main";
        // 配置生成器
        FastAutoGenerator.create(dbUrl, dbUserName, dbPassword)
                .globalConfig(builder -> {
                    builder.author("ziro") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(basePath + "\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.kg.core") // 设置父包名
                            .moduleName("zapigroup") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, basePath + "\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("z_api_group"); // 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
