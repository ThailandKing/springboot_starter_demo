package com.it.shw.hello.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Copyright: Harbin Institute of Technology.All rights reserved.
 * @Description:
 * @author: thailandking
 * @since: 2020/3/6 10:55
 * @history: 1.2020/3/6 created by thailandking
 */
@ConfigurationProperties("my-log")
@Data
public class MyLogProperty {
    //是否开启
    private boolean enabled;
    //拥有者，特殊说明
    private String owner;
}
