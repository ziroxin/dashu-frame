package com.kg.component.file;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 文件名操作 - 工具类
 *
 * @author ziro
 * @date 2022-07-02 11:13:28
 */
@Component
public class FileNameUtils extends FilenameUtils {
    public static String UPLOAD_FILE_ALLOW_EXTEND;
    public static String DEFAULT_IMAGE_FILE_EXTEND;
    public static String DEFAULT_FILE_PATH_PRE;

    /**
     * 设置允许上传的文件扩展名（默认从配置文件中读取）
     */
    @Value("${com.kg.file.allow-extend}")
    public void setUploadFileAllowExtend(String extend) {
        FileNameUtils.UPLOAD_FILE_ALLOW_EXTEND = extend;
    }

    /**
     * 图片格式扩展名（默认从配置文件中读取）
     */
    @Value("${com.kg.file.image-extend}")
    public void setDefaultImageFileExtend(String imageExtend) {
        FileNameUtils.DEFAULT_IMAGE_FILE_EXTEND = imageExtend;
    }

    /**
     * 文件存储绝对路径（默认从配置文件中读取）
     */
    @Value("${com.kg.file.default-path-pre}")
    public void setDefaultFilePathPre(String defaultPathPre) {
        FileNameUtils.DEFAULT_FILE_PATH_PRE = defaultPathPre;
    }

    /**
     * 获取保存存储路径
     *
     * @param dirName 自定义文件夹
     */
    public static String getSavePath(String dirName) {
        String path = FileNameUtils.DEFAULT_FILE_PATH_PRE + "/" + dirName + "/" +
                DateUtil.format(new Date(), "yyyyMMdd") + "/";
        return path.replaceAll("//", "/");
    }

    /**
     * 获取项目静态资源目录（本项目的resources绝对路径）
     */
    public static String getResourceStaticPath() {
        try {
            ClassPathResource cpr = new ClassPathResource("/");
            return cpr.getURL().getPath() + "static";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 删除上传文件名中的特殊字符
     */
    public static String removeSpec(String fileName) {
        return fileName.replaceAll(",", "")
                .replaceAll("\\|", "")
                .replaceAll("!", "")
                .replaceAll("@", "")
                .replaceAll("#", "")
                .replaceAll("\\$", "")
                .replaceAll("%", "")
                .replaceAll("\\^", "")
                .replaceAll("&", "");
    }
}
