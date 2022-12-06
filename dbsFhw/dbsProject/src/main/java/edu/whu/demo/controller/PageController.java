package edu.whu.demo.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 孔德昱
 * @date 2022/12/6 13:49 星期二
 */
@Api(description = "左侧管理栏")
@RestController
@RequestMapping("page")
public class PageController {

    @RequestMapping("sidebar")
    public String get_sidebar(){
        return "sidebar";
    }
}
