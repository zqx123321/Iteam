package cn.ouctechnology.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class FileUploadUtils {
    private FileUploadUtils() {

    }

    public static String upload(String path, MultipartFile file) {
        String res= null;
        try {
            String filename = UUID.randomUUID().toString() + ",jpg";
            File filepath = new File(path, filename);
            //判断路径是否存在，没有就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文档中
            File targetFile = new File(path + File.separator + filename);
            file.transferTo(targetFile);
            res=filename;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return res;
        }
    }
}
