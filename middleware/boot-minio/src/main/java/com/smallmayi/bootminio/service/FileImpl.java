package com.smallmayi.bootminio.service;

import com.smallmayi.bootminio.config.MinConfig;
import com.smallmayi.bootminio.domain.Fileinfo;
import com.smallmayi.bootminio.util.MinioUtil;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author smallmayi
 */
@Service
public class FileImpl implements FileService {
    @Autowired
    private MinConfig minConfig;

    @Override
    @SneakyThrows
    public String fileUpload(MultipartFile multipartFile) {

        String fileName = multipartFile.getOriginalFilename();
        InputStream inputStream = multipartFile.getInputStream();
        //判断桶是否存在，不存在就创建
        MinioUtil.createBucket(minConfig.getBucketName());
        MinioUtil.uploadFile(inputStream, minConfig.getBucketName(), fileName);
        inputStream.close();
        //返回文件存储地址
        String url = minConfig.getUrl() + "/" + minConfig.getBucketName() + "/" + fileName;
        return url;
    }

    @Override
    @SneakyThrows
    public void fileDown(@RequestParam("fileName") String fileName, HttpServletResponse response) {

        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setCharacterEncoding("UTF-8");
        InputStream inputStream = MinioUtil.download(minConfig.getBucketName(), fileName);
        IOUtils.copy(inputStream, response.getOutputStream());
        inputStream.close();
    }

    @Override
    @SneakyThrows
    public String fileInfo(String fileName){
        return MinioUtil.getObjectInfo(minConfig.getBucketName(),fileName);
    }

    @Override
    @SneakyThrows
    public List<Fileinfo> fileList() {
        List<Fileinfo> fileInfos = MinioUtil.listFiles(minConfig.getBucketName());
        return fileInfos;
    }

    @Override
    @SneakyThrows
    public void delete(String fileName) {
        MinioUtil.deleteObject(minConfig.getBucketName(), fileName);
    }

    @Override
    @SneakyThrows
    public List<String> bucketList() {
        List<String> bucketList = MinioUtil.listBuckets();
        return bucketList;
    }

    @Override
    @SneakyThrows
    public void bucketDelete(String bucketName) {
        MinioUtil.deleteBucket(bucketName);
    }

    @Override
    @SneakyThrows
    public List<Fileinfo> listAllFile() {
        return MinioUtil.listAllFile();
    }

    @Override
    @SneakyThrows
    public String externalUrl(String fileName) {
        return MinioUtil.getPresignedObjectUrl(minConfig.getBucketName(),fileName,10000);
    }
}
