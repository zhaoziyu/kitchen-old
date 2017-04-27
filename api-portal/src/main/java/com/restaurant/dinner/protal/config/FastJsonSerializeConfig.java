package com.restaurant.dinner.protal.config;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.support.config.FastJsonConfig;

/**
 * Created by Zhaozy on 2017-04-27.
 */
public class FastJsonSerializeConfig extends FastJsonConfig {

    public PropertyNamingStrategy propertyNamingStrategy;

    public FastJsonSerializeConfig() {
        super();

        /**
         * 设置JSON序列化和解析的字段格式
         * CamelCase	persionId
         * PascalCase	PersonId
         * SnakeCase	person_id
         * KebabCase	person-id
         */
        if (propertyNamingStrategy == null) {
            propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        }

        this.getSerializeConfig().propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        this.getParserConfig().propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    public PropertyNamingStrategy getPropertyNamingStrategy() {
        return propertyNamingStrategy;
    }

    public void setPropertyNamingStrategy(PropertyNamingStrategy propertyNamingStrategy) {
        this.propertyNamingStrategy = propertyNamingStrategy;
    }
}
