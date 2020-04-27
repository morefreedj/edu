package com.dj.edu.servicebase.exceptionhandler;



import com.dj.commonutils.R;
import com.dj.commonutils.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


//异常控制
@ControllerAdvice
@Slf4j//向日志里添加异常信息
public class GlobalExceptionHandler {

    //指定出现什么异常会调用这个方法
    //出现所有异常都会调用这个方法Exception.class
    //因为需要返回数据，所以需要添加@ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理。。");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理。。");
    }

    /**
     * 自定义异常需要手动抛出
     * 1.首先创建自定义异常类DjException，继承RuntimeException，编写异常属性，状态码和异常信息
     * 2.在统一异常类中添加自己的自定义方法（就是在本类）
     * 3.由于是自定义方法，无法自动识别没所以采用try，catch的方法实现
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DjException.class)
    @ResponseBody
    public R error(DjException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}
