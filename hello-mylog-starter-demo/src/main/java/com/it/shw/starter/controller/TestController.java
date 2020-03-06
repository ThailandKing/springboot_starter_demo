package com.it.shw.starter.controller;

import com.it.shw.hello.annotation.MyLog;
import com.it.shw.starter.entity.Student;
import org.springframework.web.bind.annotation.*;

/**
 * @Copyright: Harbin Institute of Technology.All rights reserved.
 * @Description:
 * @author: thailandking
 * @since: 2020/3/5 16:52
 * @history: 1.2020/3/5 created by thailandking
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @MyLog(desc = "测试没有参数日志记录")
    @GetMapping(value = "/param/no")
    public String testNoParams() {
        return "ok";
    }

    @MyLog(desc = "测试有RequestBody参数日志记录")
    @PostMapping(value = "/param/body")
    public Student testWithParams(@RequestBody Student student) {
        return student;
    }

    @MyLog(desc = "测试有PathVariable参数日志记录")
    @GetMapping(value = "/param/{id}")
    public Long testWithParams(@PathVariable Long id) {
        return id;
    }
}
