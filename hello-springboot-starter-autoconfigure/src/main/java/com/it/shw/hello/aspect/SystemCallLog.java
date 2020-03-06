package com.it.shw.hello.aspect;

import lombok.Data;

/**
 * @Copyright: Harbin Institute of Technology.All rights reserved.
 * @Description:
 * @author: thailandking
 * @since: 2020/3/6 9:00
 * @history: 1.2020/3/6 created by thailandking
 */
@Data
public class SystemCallLog {
    // 注解内容
    private String value;
    // 方法名
    private String methodName;
    // 参数列表 json
    private String params;
    // 用户名
    private String userName;
    // 时间
    private String callTime;
}
