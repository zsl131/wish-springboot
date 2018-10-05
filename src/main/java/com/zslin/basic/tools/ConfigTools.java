package com.zslin.basic.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by zsl-pc on 2016/9/11.
 */
@Configuration
@Component
public class ConfigTools { // extends WebMvcConfigurerAdapter

    @Value("${web.upload-path}")
    private String uploadPath;

    public static final String PUBLIC_PATH = "publicFile"+File.separator;

    /** 项目名称 */
    @Value("${spring.application.name}")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String getUploadPath() {
        return getUploadPath("");
    }

    private String getUploadPath(String basePath) {
        File f = new File(uploadPath+basePath);
        if(!f.exists()) {f.mkdirs();}
        return f.getAbsolutePath()+File.separator;
    }

    public String getUploadPath(String basePath, boolean isPublic) {
        return getUploadPath((isPublic?PUBLIC_PATH:"")+basePath);
    }

    public String getUploadPath(boolean isPublic) {
        return getUploadPath(isPublic?PUBLIC_PATH:"");
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //设置静态资源路径
        registry.addResourceHandler("*//**")
                .addResourceLocations("classpath:/", "classpath:/static/", "classpath:/public/", "file:"+File.separator+uploadPath);
//        super.addResourceHandlers(registry);
    }*/
}
