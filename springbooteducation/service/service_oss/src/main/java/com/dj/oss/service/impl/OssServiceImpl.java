package com.dj.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.dj.oss.service.OssService;
import com.dj.oss.utils.ConstandPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;


//实现上传文件
@Service
public class OssServiceImpl implements OssService {


    //上传文件到oss
    @Override
    public String uploadFileAvator(MultipartFile file) {

        //通过工具类获取值
        // Endpoint以杭州为例，其它Region请按实际情况填写。我的是北京，用类名。属性值调用
        String endpoint = ConstandPropertiesUtils.END_POINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = ConstandPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstandPropertiesUtils.KEY_SERCET;
        String bucketName = ConstandPropertiesUtils.BUCKET_NAME;

        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。获取file.getInputStream()
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String filename = file.getOriginalFilename();

            /** 因为阿里云存储文件名相同的文件时，后储存的会覆盖掉之前的
             *  处理方法：1.在文件名称里面添加随机唯一的值
             *  UUID 是指Universally Unique Identifier，翻译为中文是通用唯一识别码，
             *  UUID 的目的是让分布式系统中的所有元素都能有唯一的识别信息。
             */
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            //filename = uuid + filename;

            /**
             *  把文件按日期进行分类
             */
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");
            filename = datePath+"/"+uuid + filename;

            /**
             * 调用oss方法实现上传
             * 第一个参数 Bucket name
             * 第二个参数 上传到oss文件路径和文件名称
             */

            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回一下
            //手动拼接
            //eg:https://edu-dj.oss-cn-beijing.aliyuncs.com/example.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+filename;//先来个空
            return url;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
