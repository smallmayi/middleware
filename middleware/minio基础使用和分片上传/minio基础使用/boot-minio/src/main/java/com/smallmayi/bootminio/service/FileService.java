package com.smallmayi.bootminio.service;

import com.smallmayi.bootminio.domain.Fileinfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author smallmayi
 **/
public interface FileService {
    /**
     * 文件上传
     *
     * @param multipartFile 文件
     * @return
     */
    String fileUpload(MultipartFile multipartFile);

    /**
     * 文件下载
     *
     * @param fileName 文件
     * @param response
     * @return
     */
    void fileDown(String fileName, HttpServletResponse response);

    /**
     * 文件信息
     *
     * @param fileName 文件
     * @return
     */
    String fileInfo(String fileName);

    /**
     * 桶下面的所有文件
     *
     * @param
     * @return
     */
    List<Fileinfo> fileList();

    /**
     * 根据文件名删除文件
     *
     * @param
     * @param fileName 文件名
     */
    void delete(String fileName);

    /**
     * 查询所有桶
     *
     * @param
     * @return  所有桶的列表
     */
    List<String> bucketList();
    /**
     * 删除桶
     *
     * @param
     */
    void bucketDelete(String bucketName);

    /**
     * 所有文件列表
     *
     * @param
     * @return
     */
    List<Fileinfo> listAllFile();

    /**
     * 文件外链
     *
     * @param fileName 文件名
     * @return
     */
    String externalUrl(String fileName);
}
