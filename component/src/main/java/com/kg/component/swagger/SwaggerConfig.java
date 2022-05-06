package com.kg.component.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger生成API配置类
 *
 * @author ziro
 * @date 2019/7/24 17:28
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${com.kg.swagger.title}")
    private String title;
    @Value("${com.kg.swagger.description}")
    private String description;
    // API协议地址
    @Value("${com.kg.swagger.termsOfServiceUrl}")
    private String termsOfServiceUrl;
    @Value("${com.kg.swagger.version}")
    private String version;
    @Value("${com.kg.swagger.concat.name}")
    private String name;
    @Value("${com.kg.swagger.concat.www}")
    private String www;
    @Value("${com.kg.swagger.concat.email}")
    private String email;

    /**
     * 配置swagger注册信息
     *
     * @return
     */
    @Bean
    public ApiInfo apiInfo() {
        Contact contact = new Contact(name, www, email);
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(contact) // 联系方式
                .version(version)
                .build();
    }

    /**
     * 创建api扫描规则
     * <p>可以配置多个Docket，作为不同分组</p>
     * <p>以下配置例子：显示所有/test/开头的api</p>
     *
     * @return
     */
    @Bean
    public Docket api1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("test")
                .select()
                // 这里采用包含注解的方式来确定要显示的接口(建议使用这种)
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 以/test/开头的接口
                .paths(PathSelectors.ant("/test/**"))
                // 所有接口
                //.paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket api2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("login")
                .select()
                // 这里采用包含注解的方式来确定要显示的接口(建议使用这种)
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 以/test/开头的接口
                .paths(PathSelectors.ant("/login/**"))
                // 所有接口
                //.paths(PathSelectors.any())
                .build();
    }

}
