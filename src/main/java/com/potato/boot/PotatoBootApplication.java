package com.potato.boot;

import com.potato.boot.controller.ControllerInterceptor;
import com.potato.boot.manager.HelloAspect;
import com.potato.boot.manager.HelloAspect2;
import com.potato.boot.manager.HelloAspect3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author dehuab
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:spring-config.xml"})
public class PotatoBootApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(PotatoBootApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new ControllerInterceptor());
        registration.addPathPatterns("/");
    }

    @Bean(name = "helloAspect")
    public HelloAspect initHelloAspect() {
        return new HelloAspect();
    }

    @Bean(name = "helloAspect2")
    public HelloAspect2 initHelloAspect2() {
        return new HelloAspect2();
    }

    @Bean(name = "helloAspect3")
    public HelloAspect3 initHelloAspect3() {
        return new HelloAspect3();
    }
}
