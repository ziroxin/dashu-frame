package com.kg.component.file;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件上传 - 数据实体类
 *
 * @author ziro
 * @date 2022-07-02 14:56:27
 */
@Getter
@Setter
@ToString
public class FileUploadDTO {
    /**
     * 文件地址（http地址）
     */
    private String fileUrl;
    /**
     * 文件路径（保存数据库）
     */
    private String filePath;
    /**
     * 原文件名
     */
    private String fileOldName;
    /**
     * 存储文件名
     */
    private String fileName;
    /**
     * 文件扩展名
     */
    private String fileExtend;
    /**
     * 文件大小
     */
    private long fileSize;
}
