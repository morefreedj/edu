package com.dj.edu.servicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * 自动填充功能
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    //添加时
    public void insertFill(MetaObject metaObject) {

        this.setFieldValByName("gmtCreate", new Date(), metaObject);//这里是属性名称不是字段值
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
    //更新时
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
