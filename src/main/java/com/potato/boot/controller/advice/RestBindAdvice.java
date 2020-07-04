package com.potato.boot.controller.advice;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * @author dehuab
 */
@RestControllerAdvice
public class RestBindAdvice {
    /**
     * 去除get方式的参数空格
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 创建StringTrim编辑器
        // 构造方法中boolean参数含义为如果是空白字符串,是否转换为null
        // 即如果为true,那么 " " 会被转换为 null,否者为 ""
        StringTrimmerEditor propertyEditor = new StringTrimmerEditor(false);
        // 为String类对象注册编辑器
        binder.registerCustomEditor(String.class, propertyEditor);
    }

    /**
     * 去除post requestBody实体中的空格
     *
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.deserializerByType(String.class, new StdScalarDeserializer<Object>(String.class) {
            @Override
            public String deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
                return StringUtils.trimWhitespace(jsonParser.getValueAsString());
            }
        });
    }
}
