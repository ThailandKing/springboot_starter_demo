package com.it.shw.hello.annotation;

import java.lang.annotation.*;

/**
 * @Copyright: Harbin Institute of Technology.All rights reserved.
 * @Description:
 * @author: thailandking
 * @since: 2020/3/5 14:52
 * @history: 1.2020/3/5 created by thailandking
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLog {
    String desc() default "";
}
