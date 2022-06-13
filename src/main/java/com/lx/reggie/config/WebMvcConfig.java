package com.lx.reggie.config;

import com.lx.reggie.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author 小兴
 * @description mvc 配置类
 * @className WebMvcConfig
 * @date 2022/6/10 22:56
 */
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    // 设置静态资源访问映射
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 当前端访问 /backend 目录下的资源 tomcat 会去当前资源路径下的 resources 下面寻找
        log.info("开始进行静态资源映射");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    /**
     * 扩展 MVC 消息类型转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        // 设置对象转换器,底层使用 jackSon 将 java 对象序列化为 json 的
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        // 将上面的消息转换器,追加到 mvc 框架的转换器中
        converters.add(0, messageConverter);
    }
}
