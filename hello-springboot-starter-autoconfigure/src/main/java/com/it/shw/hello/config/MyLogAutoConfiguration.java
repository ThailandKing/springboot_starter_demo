package com.it.shw.hello.config;

import com.it.shw.hello.aspect.LogInsertAspect;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Copyright: Harbin Institute of Technology.All rights reserved.
 * @Description:
 * @author: thailandking
 * @since: 2020/3/5 16:41
 * @history: 1.2020/3/5 created by thailandking
 */
@Configuration
@EnableConfigurationProperties(MyLogProperty.class)
public class MyLogAutoConfiguration {

    private final MyLogProperty myLogProperty;

    public MyLogAutoConfiguration(MyLogProperty myLogProperty) {
        this.myLogProperty = myLogProperty;
    }

    @Bean
    public LogInsertAspect logInsertAspect() {
        return new LogInsertAspect(myLogProperty);
    }
}
