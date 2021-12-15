/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com           2021. 8. 18.  First Draft.
 *
 ****************************************************/
package kyobobook.application.biz.sample.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kyobobook.application.adapter.out.file.AwsS3RuntimeException;
import kyobobook.application.biz.common.port.out.FileManagePort;
import kyobobook.application.biz.sample.port.in.AwsS3FilePort;
import kyobobook.application.domain.file.BucketInfo;
import kyobobook.application.domain.file.ObjectInfo;

/**
 * @Project     : bo-prototype-ui
 * @FileName    : SampleFileService.java
 * @Date        : 2021. 8. 18.
 * @author      : smlee1@kyobobook.com
 * @description : AmazonS3 파일 처리 Service class
 */
@Service
public class AwsS3FileService implements AwsS3FilePort {

    private static final Logger logger = LoggerFactory.getLogger(AwsS3FileService.class);
    
    final FileManagePort fileManagePort;
    
    /**
     * Constructor
     * @param fileManagePort
     */
    public AwsS3FileService(FileManagePort fileManagePort) {
        this.fileManagePort = fileManagePort;
    }
    
    @Override
    public void uploadFilesToS3(String bucket, String path, MultipartFile file) throws AwsS3RuntimeException {
        logger.debug("//// Upload Name > " + file.getName());
        logger.debug("//// Upload OriginalFilename > " + file.getOriginalFilename());
        logger.debug("//// Upload File Size > " + file.getSize()); 
        
        fileManagePort.saveAsFile(bucket, path, file.getOriginalFilename(), file);
    }
    
    @Override
    public void uploadMultipartFilesToS3(String bucket, String path, MultipartFile file) throws AwsS3RuntimeException {
        logger.debug("//// Upload Name > " + file.getName());
        logger.debug("//// Upload OriginalFilename > " + file.getOriginalFilename());
        logger.debug("//// Upload File Size > " + file.getSize()); 
        
        fileManagePort.saveAsMultipartFile(bucket, path, file.getOriginalFilename(), file);
    }

    @Override
    public List<BucketInfo> selectBucket() throws AwsS3RuntimeException {
        return fileManagePort.selectBucket();
    }

    @Override
    public List<String> listKeysInBucket(String bucket, String path) throws AwsS3RuntimeException {
        return fileManagePort.listKeysInBucket(bucket, path);
    }

    @Override
    public void createFolder(String bucket, String path) throws AwsS3RuntimeException {
        fileManagePort.createFolder(bucket, path);
    }

    @Override
    public void deleteFolder(String bucket, String path) throws AwsS3RuntimeException {
        fileManagePort.deleteFolder(bucket, path);
    }

    @Override
    public List<ObjectInfo> listObject(String bucket, String Path) throws AwsS3RuntimeException {
        return fileManagePort.listObject(bucket, Path);
    }

    @Override
    public void deleteFile(String bucket, String key) throws AwsS3RuntimeException {
        fileManagePort.deleteFile(bucket, key);
    }

    @Override
    public void downloadFile(HttpServletRequest request
            , HttpServletResponse response
            , String bucket
            , String folderPath
            , String fileName) throws AwsS3RuntimeException {
        fileManagePort.downloadFile(request, response, bucket, folderPath, fileName);
    }
}
