package com.cq.gmall.util;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 彭国仁
 * @data 2019/9/3 9:00
 */

public class PmsUploadUtil {
    public static String uploadImage(MultipartFile multipartFile) {
        String imgUrl = "http://192.168.31.145";
        // 配置fdfs的全局链接地址
        // 获得配置文件的路径
        String tracker = PmsUploadUtil.class.getResource("/tracker.conf").getPath();

        try {
            ClientGlobal.init(tracker);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TrackerClient trackerClient = new TrackerClient();

        // 获得一个trackerServer的实例
        TrackerServer trackerServer = null;
        try {
            trackerServer = trackerClient.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 通过tracker获得一个Storage链接客户端
        StorageClient storageClient = new StorageClient(trackerServer,null);

        try {
            // 获得上传的二进制对象
            byte[] bytes = multipartFile.getBytes();
            String originalFilename = multipartFile.getOriginalFilename();
            int i = originalFilename.lastIndexOf(".");
            String extName = originalFilename.substring(i + 1);
            String[]  uploadInfos = storageClient.upload_file(bytes, extName, null);
            for (String uploadInfo : uploadInfos) {
                imgUrl += "/"+uploadInfo;

                //url = url + uploadInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgUrl;
    }
}
