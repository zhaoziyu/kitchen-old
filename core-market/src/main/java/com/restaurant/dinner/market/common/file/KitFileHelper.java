package com.restaurant.dinner.market.common.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传工具类
 *
 * @date 2016-12-22
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class KitFileHelper {
    /**
     * 文件上传至本地服务器
     * 注意！ 不支持分布式部署的文件访问
     *
     * @param file
     * @param folder
     * @return URI
     */
    public static String uploadToLocal(MultipartFile file, String folder) {
        String originalFilename = file.getOriginalFilename();
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString().replace("-", "").toUpperCase() + fileSuffix;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateDir = sdf.format(new Date());

        String path = System.getProperty("user.dir");

        // 回退两层
        path = path.substring(0,path.lastIndexOf(File.separator));
        path = path.substring(0,path.lastIndexOf(File.separator));

        String folderPath = "";
        String folderUri = "";
        if (folder != null && !folder.isEmpty()) {
            folderPath = File.separator + folder;
            folderUri = "/" + folder;
        }
        path = path + File.separator + "upload" + folderPath + File.separator + dateDir;

        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String fileURI = "/upload" + folderUri + "/" + dateDir + "/" + fileName;

        return fileURI;
    }

    /**
     * 上传文件到“中央文件服务器”
     * @param file
     * @param folder
     * @return
     */
    public static String uploadToCenterFileServer(MultipartFile file, String folder) {
        // TODO 上传文件到“中央文件服务器”例如Nginx
        return null;
    }
}
