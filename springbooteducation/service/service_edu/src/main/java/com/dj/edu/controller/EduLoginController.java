package com.dj.edu.controller;

import com.dj.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController//托管给spring进行管理
@RequestMapping("/edu/user")//请求地址
@CrossOrigin//解决跨域问题，端口号，协议号，ip地址
public class EduLoginController {

    //login
    @PostMapping("login")
    public R Login(){

        return R.ok().data("tokec","admin");
    }


    //info
    @GetMapping("info")
    public R Info(){


        return R.ok().data("roles","[admin]").data("name","admin").data("avator","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
