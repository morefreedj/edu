package com.dj.oss.utils;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


//当项目已启动，spring接口，执行一个方法，使其能够被读取
//implements InitializingBean即启动后时类中的private属性能够被读取
@Component
public class ConstandPropertiesUtils implements InitializingBean {

    //读取配置文件内容
    //属性注入注解@Value()

    //地域节点
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    //key值
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    //密码
    @Value("${aliyun.oss.file.keysecret}")
    private String keyScreet;

    //bucket名字
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    //定义公开静态常量
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SERCET;
    public static String BUCKET_NAME;


    @Override
    public void afterPropertiesSet() throws Exception {
        //取到私有属性（配置文件中的固定值）
        END_POINT = endpoint;
        KEY_ID = keyId;
        KEY_SERCET = keyScreet;
        BUCKET_NAME = bucketName;


    }
}
