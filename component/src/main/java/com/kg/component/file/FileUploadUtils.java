package com.kg.component.file;

import cn.hutool.core.io.FileUtil;
import com.kg.component.utils.GuidUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件上传 - 工具类
 *
 * @author ziro
 * @date 2022-07-02 14:54:24
 */
public class FileUploadUtils {
    /**
     * 文件上传处理
     *
     * @param request 请求体
     * @param dirName 自定义文件夹
     * @return 上传文件列表
     */
    public static List<FileUploadDTO> uploadFile(HttpServletRequest request, String dirName) throws IOException {
        List<FileUploadDTO> resultList = new ArrayList<>();
        // 上传文件请求处理
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        multipartRequest.setCharacterEncoding("UTF-8");
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> fileInput : fileMap.entrySet()) {
            // 循环处理待上传列表
            String fileInputName = fileInput.getKey();
            List<MultipartFile> fileList = multipartRequest.getFiles(fileInputName);
            for (MultipartFile multipartFile : fileList) {
                // 上传文件实体
                FileUploadDTO file = new FileUploadDTO();
                String oldFileName = FileNameUtils.removeSpec(multipartFile.getOriginalFilename());
                if (!StringUtils.hasText(oldFileName)) {
                    throw new IOException("上传文件名不正确！请检查");
                }
                file.setFileOldName(oldFileName);

                // 判断文件扩展名
                String extend = FileTypeUtils.getFileType(multipartFile.getBytes()).toLowerCase();
                if (FileNameUtils.UPLOAD_FILE_ALLOW_EXTEND.toLowerCase().indexOf(extend) < 0) {
                    throw new IOException("您上传的文件格式不正确！请检查");
                }
                file.setFileExtend(extend);

                // 处理文件名、路径
                file.setFileName(GuidUtils.getUuid32() + "." + extend);
                file.setFilePath(FileNameUtils.getSavePath(dirName) + file.getFileName());
                file.setFileSize(multipartFile.getSize());

                // 准备保存文件
                String saveFileStr = FileNameUtils.getResourceStaticPath() + file.getFilePath();
                File saveFile = new File(saveFileStr.replaceAll("//", "/"));
                FileUtil.mkParentDirs(saveFile);
                FileCopyUtils.copy(multipartFile.getBytes(), saveFile);
                resultList.add(file);
            }
        }
        return resultList;
    }
}
