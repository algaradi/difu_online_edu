package com.wangdifu.ossservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyuncs.exceptions.ClientException;
import com.wangdifu.ossservice.service.OssService;
import com.wangdifu.ossservice.utils.OssPropertisUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-7 , 0007 19:07
 * @Version: 1.0
 */
@Service
public class OssServiceImpl implements OssService {
  @Override
  public String uploateAvatarFile(MultipartFile file) {

    //获取阿里云存储相关常量
    String endPoint = OssPropertisUtils.END_POINT;
    String accessKeyId = OssPropertisUtils.ACCESS_KEY_ID;
    String accessKeySecret = OssPropertisUtils.ACCESS_KEY_SECRET;
    String bucketName = OssPropertisUtils.BUCKET_NAME;

    // 获取上传文件输入流
    InputStream inputStream = null;
    // 文件上传到阿里云oss的路径和名称
    String filePath = null;
    // 上传的整个路径
    String uploadeUrl = null;
    //ossClient对象
    OSS ossClient = null;
    try {
      //获取file文件的原始名称
      String fileName = file.getOriginalFilename();
      //避免文件名称冲突可以结案是唯一id
      String uuid = UUID.randomUUID().toString().replaceAll("-","");
      //加上文件划分 可以按照日期来划分在阿里云保存格式可以如下 avatar/2022/02/22/文件
      // 用joda日期工具包
      String dateTime = new DateTime().toString("yyyy/MM/dd");

      filePath = "avatar/"+dateTime+"/"+uuid+fileName;
      uploadeUrl = "http://" + bucketName + "." + endPoint + "/" + filePath;
      inputStream = file.getInputStream();

       // 创建OSSClient实例。
      ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

      // 创建PutObject请求。
      ossClient.putObject(bucketName, filePath, inputStream);

      // 返回整个访问路径
      return uploadeUrl;

    } catch (OSSException oe ) {
      System.out.println("Caught an OSSException, which means your request made it to OSS, "
              + "but was rejected with an error response for some reason.");
      System.out.println("Error Message:" + oe.getErrorMessage());
      System.out.println("Error Code:" + oe.getErrorCode());
      System.out.println("Request ID:" + oe.getRequestId());
      System.out.println("Host ID:" + oe.getHostId());
    } catch (Exception io){
      System.out.println("上传的文件有问题");
      io.printStackTrace();
    }finally {
      if (ossClient != null) {
        // 关闭资源
        ossClient.shutdown();
      }
    }

    return uploadeUrl;
  }


  // 上传课程封面
  @Override
  public String uploateCoverFile(MultipartFile file) {

    //获取阿里云存储相关常量
    String endPoint = OssPropertisUtils.END_POINT;
    String accessKeyId = OssPropertisUtils.ACCESS_KEY_ID;
    String accessKeySecret = OssPropertisUtils.ACCESS_KEY_SECRET;
    String bucketName = OssPropertisUtils.BUCKET_NAME;

    // 获取上传文件输入流
    InputStream inputStream = null;
    // 文件上传到阿里云oss的路径和名称
    String filePath = null;
    // 上传的整个路径
    String uploadeUrl = null;
    //ossClient对象
    OSS ossClient = null;
    try {
      //获取file文件的原始名称
      String fileName = file.getOriginalFilename();
      //避免文件名称冲突可以结案是唯一id
      String uuid = UUID.randomUUID().toString().replaceAll("-","");
      //加上文件划分 可以按照日期来划分在阿里云保存格式可以如下 avatar/2022/02/22/文件
      // 用joda日期工具包
      String dateTime = new DateTime().toString("yyyy/MM/dd");

      filePath = "cover/"+dateTime+"/"+uuid+fileName;
      uploadeUrl = "http://" + bucketName + "." + endPoint + "/" + filePath;
      inputStream = file.getInputStream();

      // 创建OSSClient实例。
      ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

      // 创建PutObject请求。
      ossClient.putObject(bucketName, filePath, inputStream);

      // 返回整个访问路径
      return uploadeUrl;

    } catch (OSSException oe ) {
      System.out.println("Caught an OSSException, which means your request made it to OSS, "
              + "but was rejected with an error response for some reason.");
      System.out.println("Error Message:" + oe.getErrorMessage());
      System.out.println("Error Code:" + oe.getErrorCode());
      System.out.println("Request ID:" + oe.getRequestId());
      System.out.println("Host ID:" + oe.getHostId());
    } catch (Exception io){
      System.out.println("上传的文件有问题");
      io.printStackTrace();
    }finally {
      if (ossClient != null) {
        // 关闭资源
        ossClient.shutdown();
      }
    }

    return uploadeUrl;
  }
}
