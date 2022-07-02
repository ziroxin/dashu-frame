package com.kg.core.web;

import com.kg.component.file.FileNameUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置静态资源映射
 *
 * @author ziro
 * @date 2022-07-02 21:54:31
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String fileUploadPath = FileNameUtils.getResourceStaticPath() + "/" + FileNameUtils.DEFAULT_FILE_PATH_PRE + "/";
        registry.addResourceHandler("/" + FileNameUtils.DEFAULT_FILE_PATH_PRE + "/**")
                .addResourceLocations("file:" + fileUploadPath.replaceAll("//", "/"));
    }
}
