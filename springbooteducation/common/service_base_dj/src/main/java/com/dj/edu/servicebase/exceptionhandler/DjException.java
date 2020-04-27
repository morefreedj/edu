package com.dj.edu.servicebase.exceptionhandler;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   //get，set方法都有了@Data
@AllArgsConstructor     //生成有参数的构造方法
@NoArgsConstructor      //生成无参数的构造
public class DjException extends RuntimeException{


    private Integer code;  //状态码

    private String msg;    //异常信息

    @Override
    public String toString() {
        return "GuliException{" +
        "message=" + this.getMessage() +
        ", code=" + code +
        '}';
    }

}
