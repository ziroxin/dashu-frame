package com.kg.component.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import com.kg.component.utils.GuidUtils;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * 其他文件保存工具类
 *
 * @author ziro
 * @date 2022-07-28 19:40:58
 */
public class FileSaveUtils {
    /**
     * base64转存图片
     *
     * @param base64Data base64数据
     * @param dirName    自定义路径
     * @return 存储文件实体
     */
    public static FileUploadDTO base64ToJpg(String base64Data, String dirName) {
        // 把base64文件，解析成bytes
        byte[] buffer = Base64Utils.decodeFromString(base64Data);
        // 然后保存
        return FileSaveUtils.bufferToFile(buffer, dirName, "jpg");
    }

    /**
     * byte[]转存文件
     *
     * @param buffer  源文件byte[]
     * @param dirName 自定义路径
     * @param extend  文件扩展名
     * @return 存储文件实体
     */
    public static FileUploadDTO bufferToFile(byte[] buffer, String dirName, String extend) {
        try {
            // 上传文件实体
            FileUploadDTO file = new FileUploadDTO();
            file.setFileExtend(extend);
            // 处理文件名、路径
            file.setFileName(GuidUtils.getUuid32() + "." + extend);
            file.setFilePath(FileNameUtils.getSavePath(dirName) + file.getFileName());
            // 准备保存文件
            String saveFileStr = FileNameUtils.getResourceStaticPath() + file.getFilePath();
            File saveFile = new File(saveFileStr.replaceAll("//", "/"));
            FileUtil.mkParentDirs(saveFile);
            FileCopyUtils.copy(buffer, saveFile);
            file.setFileSize(saveFile.length());
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
