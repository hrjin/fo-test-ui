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
package kyobobook.application.biz.common.port.out;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import kyobobook.application.adapter.out.file.AwsS3RuntimeException;
import kyobobook.application.domain.file.BucketInfo;
import kyobobook.application.domain.file.ObjectInfo;

/**
 * @Project     : bo-prototype-ui
 * @FileName    : FileManagePort.java
 * @Date        : 2021. 8. 18.
 * @author      : smlee1@kyobobook.com
 * @description : Service 에서 S3 파일 관리를 위한 Port 이다. 
 */
public interface FileManagePort {
    
    /**
     * @Method      : selectBucket
     * @Date        : 2021. 8. 18.
     * @author      : smlee1@kyobobook.com
     * @description : Bucket 목록을 조회 한다. 
     * @return
     * @throws AwsS3RuntimeException - S3 파일 처리시 오류가 발생하는 경우
     */
    List<BucketInfo> selectBucket() throws AwsS3RuntimeException ;
    
    /**
     * @Method      : listKeysInBucket
     * @Date        : 2021. 8. 18.
     * @author      : smlee1@kyobobook.com
     * @description : default 버킷의 하위 폴더를 조회 한다.
     * @param bucket - 조회 대상 버킷
     * @param path - 파일의 경로
     * @return
     * @throws AwsS3RuntimeException - S3 파일 처리시 오류가 발생하는 경우
     */
    List<String> listKeysInBucket(String bucket, String path) throws AwsS3RuntimeException ;

    /**
     * @Method      : exists
     * @Date        : 2021. 8. 18.
     * @author      : smlee1@kyobobook.com
     * @description : 파일이 존재 하는지 조회 한다.
     * @param bucket - 조회대상 버킷
     * @param path - 파일의 경로
     * @param name - 파일명
     * @return
     * @throws AwsS3RuntimeException - S3 파일 처리시 오류가 발생하는 경우
     */
    boolean exists(String bucket, String path, String name) throws AwsS3RuntimeException ;
    
    /**
     * @Method      : deleteFile
     * @Date        : 2021. 8. 19.
     * @author      : smlee1@kyobobook.com
     * @description : 지정된 위치의 파일을 삭제한다.
     * @param bucket - 삭제대상 버킷
     * @param key - 파일의 key, 해당 파일의 풀 경로
     * @throws AwsS3RuntimeException - S3 파일 처리시 오류가 발생하는 경우
     */
    void deleteFile(String bucket, String key) throws AwsS3RuntimeException ;
 
    /**
     * @Method      : createFolder
     * @Date        : 2021. 8. 18.
     * @author      : smlee1@kyobobook.com
     * @description : 폴더(path)를 생성한다.
     * @param bucket - 생성대상 버킷
     * @param path - 폴더의 경로
     * @throws AwsS3RuntimeException - S3 파일 처리시 오류가 발생하는 경우
     */
    void createFolder(String bucket, String path) throws AwsS3RuntimeException ;
    
    /**
     * @Method      : deleteFolder
     * @Date        : 2021. 8. 18.
     * @author      : smlee1@kyobobook.com
     * @description : 폴더(path)를 삭제한다.
     * @param bucket - 삭제대상 버킷
     * @param path - 폴더의 경로
     * @throws AwsS3RuntimeException - S3 파일 처리시 오류가 발생하는 경우
     */
    void deleteFolder(String bucket, String path) throws AwsS3RuntimeException ;
    
    /**
     * @Method      : saveAsFile
     * @Date        : 2021. 8. 24.
     * @author      : smlee1@kyobobook.com
     * @description : 경로에 파일을 업로드 한다.
     * @param bucket - 파일 등록 대상 버킷
     * @param path - 폴더의 경로
     * @param name - 파일 명
     * @param file - 업로드 대상 파일
     * @throws AwsS3RuntimeException - S3 파일 처리시 오류가 발생하는 경우
     */
    void saveAsFile(String bucket, String path, String name, MultipartFile file) throws AwsS3RuntimeException;
    
    /**
     * @Method      : saveAsMultipartFile
     * @Date        : 2021. 8. 24.
     * @author      : smlee1@kyobobook.com
     * @description : 경로에 대용량 파일을 업로드 한다.(5GB 이상)
     * @param bucket - 파일 등록 대상 버킷
     * @param path - 폴더의 경로
     * @param name - 파일 명
     * @param file - 업로드 대상 파일
     * @throws AwsS3RuntimeException - S3 파일 처리시 오류가 발생하는 경우
     */
    void saveAsMultipartFile(String bucket, String path, String name, MultipartFile file) throws AwsS3RuntimeException;
    
    /**
     * @Method      : getInputStream
     * @Date        : 2021. 8. 18.
     * @author      : smlee1@kyobobook.com
     * @description : 파일을 다운로드 하기위한 inputStream 을 가져온다.
     * @param bucket - 파일 조회 대상 버킷
     * @param path - 파일의 경로
     * @param name - 파일 명
     * @return
     * @throws AwsS3RuntimeException - S3 파일 처리시 오류가 발생하는 경우
     */
    InputStream getInputStream(String bucket, String path, String name) throws AwsS3RuntimeException ;
    
    /**
     * @Method      : listObject
     * @Date        : 2021. 8. 19.
     * @author      : smlee1@kyobobook.com
     * @description : 파일 목록 조회
     * @param bucket - 파일 등록 대상 버킷
     * @param path - 파일의 경로
     * @return
     * @throws AwsS3RuntimeException - S3 파일 처리시 오류가 발생하는 경우
     */
    List<ObjectInfo> listObject(String bucket, String path) throws AwsS3RuntimeException ;
    
    /**
     * @Method      : downloadFile
     * @Date        : 2021. 8. 20.
     * @author      : smlee1@kyobobook.com
     * @description : S3 파일을 다운로드 한다.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     * @param bucket - 파일 등록 대상 버킷
     * @param folderPath - 폴더의 경로
     * @param fileName - 파일명
     * @throws AwsS3RuntimeException - S3 파일 처리시 오류가 발생하는 경우
     */
    void downloadFile(HttpServletRequest request, HttpServletResponse response, String bucket, String folderPath, String fileName) throws AwsS3RuntimeException ;
}
