/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com      2021. 8. 18.
 *
 ****************************************************/
package kyobobook.application.adapter.out.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import kyobobook.application.biz.common.port.out.FileManagePort;
import kyobobook.application.domain.file.BucketInfo;
import kyobobook.application.domain.file.ObjectInfo;
import kyobobook.common.PathParser;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : S3FileManageAdapter.java
 * @Date        : 2021. 8. 18.
 * @author      : smlee1@kyobobook.com
 * @description : AWS S3 에 파일을 처리하는 Adapter
 */
@Component
public class AwsS3Adapter implements FileManagePort {
    
    private static final Logger logger = LoggerFactory.getLogger(AwsS3Adapter.class);
            
    private final AmazonS3 amazonS3;
    
    private final MessageSourceAccessor messageSource;
    
    public AwsS3Adapter(AmazonS3 amazonS3, MessageSourceAccessor messageSource) {
        this.amazonS3 = amazonS3;
        this.messageSource = messageSource;
    }
    
    @Override
    public boolean exists(String bucket, String path, String name) throws AwsS3RuntimeException {
        String checkPath = PathParser.concatPath(path, name);
        logger.debug("/// AwsS3Adapter.exists checkPath > " + checkPath);
        try {
            return amazonS3.doesObjectExist(bucket, checkPath);
        } catch (AmazonServiceException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.service"), e);
        } catch (SdkClientException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.client"), e);
        } catch (Exception e) {
            throw new AwsS3RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(String bucket, String key) throws AwsS3RuntimeException {
        String deletePath = PathParser.parseFilePath(key);
        logger.debug("/// AwsS3Adapter.deleteFile deletePath > " + deletePath);
        try {
            amazonS3.deleteObject(bucket, deletePath);
        } catch (AmazonServiceException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.service"), e);
        } catch (SdkClientException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.client"), e);
        } catch (Exception e) {
            throw new AwsS3RuntimeException(e);
        }
    }

    @Override
    public void createFolder(String bucket, String path) throws AwsS3RuntimeException {
        ObjectMetadata om = new ObjectMetadata();
        om.setContentLength(0L);
        om.setContentType("application/x-directory");
        
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[0]);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, PathParser.parseFolderPath(path), byteArrayInputStream, om);
            
            this.amazonS3.putObject(putObjectRequest);
            byteArrayInputStream.close();
        } catch (IOException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.io"), e);
        } catch (AmazonServiceException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.service"), e);
        } catch (SdkClientException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.client"), e);
        } catch (Exception e) {
            throw new AwsS3RuntimeException(e);
        }
    }

    @Override
    public void deleteFolder(String bucket, String path) throws AwsS3RuntimeException {
        String targetPath =  PathParser.parseFolderPath(path);
        try {
            if(this.amazonS3.doesBucketExistV2(bucket)) {
                ObjectListing objectList = this.amazonS3.listObjects(bucket, targetPath);
                objectList.getObjectSummaries().stream().forEach(objectSummary -> this.amazonS3.deleteObject(bucket, objectSummary.getKey()));
            
                this.amazonS3.deleteObject(bucket, targetPath);
            }
        } catch (AmazonServiceException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.service"), e);
        } catch (SdkClientException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.client"), e);
        } catch (Exception e) {
            throw new AwsS3RuntimeException(e);
        }
            
    }

    @Override
    public void saveAsFile(String bucket, String path, String name, MultipartFile file) throws AwsS3RuntimeException {
        String filePath = PathParser.parseFolderPath(path) + name;
        if(file != null && file.getSize() > 0) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());

            try {
//                this.amazonS3.putObject(new PutObjectRequest(bucket, filePath, file.getInputStream(), objectMetadata)
//                            .withCannedAcl(CannedAccessControlList.PublicRead));
                this.amazonS3.putObject(new PutObjectRequest(bucket, filePath, file.getInputStream(), objectMetadata));
            } catch (IOException e) {
                throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.io"), e);
            } catch (AmazonServiceException e) {
                throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.service"), e);
            } catch (SdkClientException e) {
                throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.client"), e);
            } catch (Exception e) {
                throw new AwsS3RuntimeException(e);
            }
        }
    }
    
    @Override
    public void saveAsMultipartFile(String bucket, String path, String name, MultipartFile file) throws AwsS3RuntimeException {
        String filePath = PathParser.parseFolderPath(path) + name;
        if(file != null && file.getSize() > 0) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            
            TransferManager transferManager = TransferManagerBuilder.standard()
                    .withS3Client(amazonS3)
                    .build();
            
            Upload upload;
            try {
                upload = transferManager.upload(new PutObjectRequest(bucket, filePath, file.getInputStream(), objectMetadata));
//                upload = transferManager.upload(new PutObjectRequest(bucket, filePath, file.getInputStream(), objectMetadata)
//                        .withCannedAcl(CannedAccessControlList.PublicRead));
                upload.waitForCompletion();
            } catch (AmazonServiceException e) {
                throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.service"), e);
            } catch (AmazonClientException e) {
                throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.client"), e);
            } catch (IOException e) {
                throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.io"), e);
            } catch (InterruptedException e) {
                throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.interrupted"), e);
            } catch (Exception e) {
                throw new AwsS3RuntimeException(e);
            }
            
            logger.info("/// Multipart File upload complete (" + filePath + ")");
        }
    }
    
    @Override
    public InputStream getInputStream(String bucket, String path, String name) throws AwsS3RuntimeException {
        String filePath = PathParser.parseFolderPath(path) + name;
        return this.getObject(bucket, filePath).getObjectContent();
    }
    
    /**
     * @Method      : getFileUrl
     * @Date        : 2021. 8. 26.
     * @author      : smlee1@kyobobook.com
     * @description : 파일의 url 을 조회
     * @param bucket - 조회 등록 대상 버킷
     * @param path - 파일의 경로
     * @param name - 파일 명
     * @return
     * @throws AwsS3RuntimeException
     */
    public String getFileUrl(String bucket, String path, String name) throws AwsS3RuntimeException {
        String filePath = path + name;
        try {
            return this.amazonS3.generatePresignedUrl(new GeneratePresignedUrlRequest(bucket, filePath)).toString();
        } catch (SdkClientException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.client"), e);
        } catch (Exception e) {
            throw new AwsS3RuntimeException(e);
        }
    }
    
    @Override
    public List<BucketInfo> selectBucket() throws AwsS3RuntimeException {
        
        List<BucketInfo> resultBucketList = new ArrayList<BucketInfo>();
        try {
            this.amazonS3.listBuckets().stream().forEach(bucket -> {
                Owner owner = bucket.getOwner();
                BucketInfo sampleBucket = BucketInfo.builder()
                        .creationDate(bucket.getCreationDate())
                        .name(bucket.getName())
                        .displayName(owner.getDisplayName())
                        .id(owner.getId())
                        .build();
                resultBucketList.add(sampleBucket);
            });
        } catch (AmazonServiceException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.service"), e);
        } catch (SdkClientException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.client"), e);
        } catch (Exception e) {
            throw new AwsS3RuntimeException(e);
        }
        
        return resultBucketList;
    }

    @Override
    public List<String> listKeysInBucket(String bucket, String path) throws AwsS3RuntimeException {
        boolean isTopLevel = false;
        String delimiter = "/";
        if (path.equals("") || path.equals(delimiter)) {
            isTopLevel = true;
        }
        if (!path.endsWith(delimiter)) {
            path += delimiter;
        }

        ListObjectsRequest listObjectsRequest = null;
        if (isTopLevel) {
            listObjectsRequest = new ListObjectsRequest()
                    .withBucketName(bucket)
                    .withDelimiter(delimiter);
        } else {
            listObjectsRequest = new ListObjectsRequest()
                    .withBucketName(bucket)
                    .withPrefix(path)
                    .withDelimiter(delimiter);
        }
        
        ObjectListing objects = null;
        try {
            
            objects = this.amazonS3.listObjects(listObjectsRequest);
        } catch (AmazonServiceException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.service"), e);
        } catch (SdkClientException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.client"), e);
        } catch (Exception e) {
            throw new AwsS3RuntimeException(e);
        }
        
        return objects.getCommonPrefixes();
    }

    @Override
    public List<ObjectInfo> listObject(String bucket, String path) throws AwsS3RuntimeException {
        
        List<ObjectInfo> objectList = new ArrayList<ObjectInfo>();
        
        try {
            ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
                    .withBucketName(bucket)
                    .withPrefix(path);
            
            ListObjectsV2Result listObjectsResult = this.amazonS3.listObjectsV2(listObjectsRequest);
            listObjectsResult.getObjectSummaries().stream()
            .filter(objectSummary -> objectSummary.getSize() > 0)
            .forEach(objectSummary -> {
                Owner owner = objectSummary.getOwner();
                
                ObjectInfo objectInfo = ObjectInfo.builder()
                        .bucketName(objectSummary.getBucketName())
                        .eTag(objectSummary.getETag())
                        .key(objectSummary.getKey())
                        .lastModified(objectSummary.getLastModified())
                        .size(objectSummary.getSize())
                        .displayName(owner == null ? "" : owner.getDisplayName())
                        .id(owner == null ? "" : owner.getId())
                        .build();
                
                objectList.add(objectInfo);
            });
        } catch (AmazonServiceException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.service"), e);
        } catch (SdkClientException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.client"), e);
        } catch (Exception e) {
            throw new AwsS3RuntimeException(e);
        }
        return objectList;
    }
    
    @Override
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, String bucket, String folderPath, String fileName) throws AwsS3RuntimeException {
        InputStream in = null;
        OutputStream out = null;
        
        try {
            String filePath = PathParser.parseFolderPath(folderPath) + fileName;
            S3Object fullObject = this.getObject(bucket, filePath);

            response.setContentType(fullObject.getObjectMetadata().getContentType());
            this.setDisposition(fileName, request, response);
            
            in = fullObject.getObjectContent();
            
            out = response.getOutputStream();
            FileCopyUtils.copy(in, out);

            out.flush();
        } catch(AwsS3RuntimeException e) {
            throw e;
        } catch (IOException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.io"), e);
        } catch (Exception e) {
            throw new AwsS3RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.io"), e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.io"), e);
                }
            }
        }
    }
    
    /**
     * @Method      : getObject
     * @Date        : 2021. 8. 18.
     * @author      : smlee1@kyobobook.com
     * @description : S3 의 Object 를 조회한다.
     * @param bucket - 조회 등록 대상 버킷
     * @param filePath
     * @return
     * @throws AwsS3RuntimeException - S3 파일 처리시 오류가 발생하는 경우
     */
    private S3Object getObject(String bucket, String filePath) throws AwsS3RuntimeException {
        try {
            return this.amazonS3.getObject(bucket, filePath);
        } catch (AmazonServiceException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.service"), e);
        } catch (SdkClientException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.client"), e);
        } catch (Exception e) {
            throw new AwsS3RuntimeException(e);
        }
    }
    
    /**
     * @Method      : getBrowser
     * @Date        : 2021. 8. 20.
     * @author      : smlee1@kyobobook.com
     * @description : 브라우저 종류를 조회 한다.
     * @param request
     * @return
     */
    private String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");

        if (header.indexOf("MSIE") > -1 || header.indexOf("Trident") > -1) {
            return "MSIE";
        } else if (header.indexOf("Chrome") > -1) {
            return "Chrome";
        } else if (header.indexOf("Opera") > -1) {
            return "Opera";
        }
        return "Firefox";
    }
    
    /**
     * @Method      : setDisposition
     * @Date        : 2021. 8. 20.
     * @author      : smlee1@kyobobook.com
     * @description : content-Disposition 셋팅
     * @param filename - 파일명
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     * @throws AwsS3RuntimeException - S3 파일 처리시 오류가 발생하는 경우
     */
    private void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws AwsS3RuntimeException {
        String browser = this.getBrowser(request);

        String dispositionPrefix = "attachment; filename=";
        String encodedFilename = null;

        try {
            if (browser.equals("MSIE")) {
                encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
            } else if (browser.equals("Firefox")) {
                encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
            } else if (browser.equals("Opera")) {
                encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
            } else if (browser.equals("Chrome")) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < filename.length(); i++) {
                    char c = filename.charAt(i);
                    if (c > '~') {
                        sb.append(URLEncoder.encode("" + c, "UTF-8"));
                    } else {
                        sb.append(c);
                    }
                }
                encodedFilename = sb.toString();
            } else {
                throw new AwsS3RuntimeException("Not supported browser");
            }
            
            response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);
            
            if ("Opera".equals(browser)){
                response.setContentType("application/octet-stream;charset=UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            throw new AwsS3RuntimeException(messageSource.getMessage("aws.s3.error.encoding"), e);
        }
    }
}
