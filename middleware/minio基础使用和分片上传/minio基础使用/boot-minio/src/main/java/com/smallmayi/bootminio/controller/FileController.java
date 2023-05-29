package com.smallmayi.bootminio.controller;

import com.smallmayi.bootminio.domain.Fileinfo;
import com.smallmayi.bootminio.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author smallmayi
 *
 * minio文件基础操作
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param multipartFile 文件
     * @return 文件地址
     */
    @PostMapping("/upload")
    public String fileUpload(@RequestParam(name = "file") MultipartFile multipartFile) {
        String fileUrl = fileService.fileUpload(multipartFile);
        return fileUrl;
    }

    /**
     * 文件下载
     *
     * @param fileName 文件
     */
    @GetMapping("/down")
    public void fileDown(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        fileService.fileDown(fileName, response);
    }

    /**
     * 文件信息
     *
     * @param fileName 文件
     */
    @GetMapping("/info")
    public String fileDown(@RequestParam("fileName") String fileName) {
        String fileInfo = fileService.fileInfo(fileName);
        //返回值如下，桶名，文件名，时间，字节数，可以自己封装
        // ObjectStat{bucket=test, object=xxx.docx, last-modified=2023-05-15T03:03:39Z, size=78326}
        return fileInfo;
    }


    /**
     * 文件删除
     *
     * @param fileName 文件
     */
    @GetMapping("/delete")
    public String delete(String fileName) {
        fileService.delete(fileName);
        return "删除成功";
    }

    /**
     * 查询一个桶所有文件
     *
     * @return 文件列表
     */
    @GetMapping("/list")
    public List<Fileinfo> fileList() {
        List<Fileinfo> fileInfos = fileService.fileList();
        return fileInfos;
    }

    /**
     * 查询minio所有文件
     *
     * @return 文件列表
     */
    @GetMapping("/listAllFile")
    public List<Fileinfo> listAllFile() {
        List<Fileinfo> fileInfos = fileService.listAllFile();
        return fileInfos;
    }

    /**
     * 列出所有的桶
     */
    @GetMapping("/bucketList")
    public List<String> bucketList() {
        List<String> bucketList = fileService.bucketList();
        return bucketList;
    }

    /**
     * 删除一个桶
     */
    @GetMapping("/bucketDelete")
    public void bucketDelete(String bucketName) {
        fileService.bucketDelete(bucketName);
    }

    /**
     * 生成文件外链，可以免权限下载
     */
    @GetMapping("/externalUrl")
    public String externalUrl(String fileName) {
        String externalUrl = fileService.externalUrl(fileName);
        return externalUrl;
    }
}
