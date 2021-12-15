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
package kyobobook.application.adapter.in.controller.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;
import kyobobook.application.adapter.out.file.AwsS3RuntimeException;
import kyobobook.application.biz.sample.port.in.AwsS3FilePort;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : AwsS3FileController.java
 * @Date        : 2021. 8. 18.
 * @author      : smlee1@kyobobook.com
 * @description : File 처리 테스트를 위한 RestController
 */
// @Api(tags = "AwsS3FileController")
@RestController
@RequestMapping("/file")
public class AwsS3FileController {
    
    private static final Logger logger = LoggerFactory.getLogger(AwsS3FileController.class);

    private final AwsS3FilePort sampleFileService;
    
    private final MessageSourceAccessor messageSource;
    
    /**
     * Constructor
     * @param sampleFileService
     * @param messageSource
     */
    public AwsS3FileController(AwsS3FilePort sampleFileService
            , MessageSourceAccessor messageSource) {
        this.sampleFileService = sampleFileService;
        this.messageSource = messageSource;
    }
    
    /**
     * @Method      : s3FileUpload
     * @Date        : 2021. 8. 19.
     * @author      : smlee1@kyobobook.com
     * @description : S3 파일 업로드
     *                path 가 없는 경우 Root Bucket 에 저장
     * @param request - {@link MultipartHttpServletRequest}
     * @param path - 업로드 대상 폴더 경로(폴더의 구분은 '-' 으로 함)
     * @return
     * @throws AwsS3RuntimeException
     */
    // @ApiOperation(value="파일 업로드(50GB 미만)")
    @PostMapping(value = {"/upload", "/upload/{path}"})
    public ResponseEntity<?> s3FileUpload(MultipartHttpServletRequest request
            , @PathVariable Optional<String> path) throws AwsS3RuntimeException {
        
        String paramPath = "";
        if(path.isPresent()) {
            paramPath = path.get().replaceAll("-", "/");
        }
        
        logger.debug("/// Param Email > " + request.getParameter("exampleInputEmail1"));
        logger.debug("/// Param Password > " + request.getParameter("exampleInputPassword1"));
        
        String bucket = request.getParameter("bucket");
        Iterator<String> fileIter = request.getFileNames();
        
        while(fileIter.hasNext()) {
            List<MultipartFile> mFileList = request.getFiles(fileIter.next());
            for(MultipartFile mFile : mFileList) {
                logger.debug("////// " + mFile.getName());
                sampleFileService.uploadFilesToS3(bucket, paramPath, mFile);
            }
        }
        
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("resultMessage", messageSource.getMessage("common.process.complete"));
        
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
    
    /**
     * @Method      : s3FileMultipartUpload
     * @Date        : 2021. 8. 23.
     * @author      : smlee1@kyobobook.com
     * @description : S3 대용량 파일 업로드
     *                path 가 없는 경우 Root Bucket 에 저장
     * @param request - {@link MultipartHttpServletRequest}
     * @param path - 업로드 대상 폴더 경로(폴더의 구분은 '-' 으로 함)
     * @return
     * @throws AwsS3RuntimeException
     */
    // @ApiOperation(value="대용량 파일 업로드(50GB 이상)")
    @PostMapping(value = {"/multipart-upload", "/multipart-upload/{path}"})
    public ResponseEntity<?> s3FileMultipartUpload(MultipartHttpServletRequest request
            , @PathVariable Optional<String> path) throws AwsS3RuntimeException {
        
        String paramPath = "";
        if(path.isPresent()) {
            paramPath = path.get().replaceAll("-", "/");
        }
        
        logger.debug("/// Param Email > " + request.getParameter("exampleInputEmail1"));
        logger.debug("/// Param Password > " + request.getParameter("exampleInputPassword1"));
        
        String bucket = request.getParameter("bucket");
        Iterator<String> fileIter = request.getFileNames();
        
        while(fileIter.hasNext()) {
            List<MultipartFile> mFileList = request.getFiles(fileIter.next());
            for(MultipartFile mFile : mFileList) {
                logger.debug("////// " + mFile.getName());
                sampleFileService.uploadMultipartFilesToS3(bucket, paramPath, mFile);
            }
        }
        
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("resultMessage", messageSource.getMessage("common.process.complete"));
        
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
    
    /**
     * @Method      : getBuckets
     * @Date        : 2021. 8. 19.
     * @author      : smlee1@kyobobook.com
     * @description : Root Bucket 목록을 조회 한다.
     * @return
     */
    // @ApiOperation(value="버킷 목록 조회")
    @GetMapping(value = "/buckets")
    public ResponseEntity<?> getBuckets() throws AwsS3RuntimeException {
        return new ResponseEntity<>(sampleFileService.selectBucket(), HttpStatus.OK);
    }
    
    /**
     * @Method      : selectSubFolder
     * @Date        : 2021. 8. 19.
     * @author      : smlee1@kyobobook.com
     * @description : Root Bucket 하위의 Folder 목록을 조회 한다.
     * @param bucket - 조회 대상 Bucket
     * @param path - 조회 대상 폴더 경로(폴더의 구분은 '-' 으로 함)
     * @return
     * @throws AwsS3RuntimeException
     */
    // @ApiOperation(value="폴더 목록 조회")
    @GetMapping(value = {"/folder", "/folder/{path}"})
    public ResponseEntity<?> selectFolder(@RequestParam("bucket") String bucket, @PathVariable Optional<String> path) throws AwsS3RuntimeException {
        String paramPath = "";
        if(path.isPresent()) {
            paramPath = path.get().replaceAll("-", "/");
        }
        
        return new ResponseEntity<>(sampleFileService.listKeysInBucket(bucket, paramPath), HttpStatus.OK);
    }
    
    /**
     * @Method      : createFolder
     * @Date        : 2021. 8. 19.
     * @author      : smlee1@kyobobook.com
     * @description : Root Bucket 하위의 Folder 를 생성한다.
     * @param bucket - 생성 대상 Bucket
     * @param path - 생성 대상 폴더 경로(폴더의 구분은 '-' 으로 함)
     * @return
     * @throws AwsS3RuntimeException
     */
    // @ApiOperation(value="Bucket 의 폴더 생성")
    @PostMapping(value = "/folder/{path}")
    public ResponseEntity<?> createFolder(@RequestParam("bucket") String bucket, @PathVariable String path) throws AwsS3RuntimeException {
        
        sampleFileService.createFolder(bucket, path.replaceAll("-", "/"));
        
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("resultMessage", messageSource.getMessage("common.process.complete"));
        
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
    
    /**
     * @Method      : deleteFolder
     * @Date        : 2021. 8. 19.
     * @author      : smlee1@kyobobook.com
     * @description : Root Bucket 하위의 Folder 를 삭제한다.
     * @param bucket - 삭제 대상 Bucket
     * @param path - 삭제 대상 폴더 경로(폴더의 구분은 '-' 으로 함)
     * @return
     * @throws AwsS3RuntimeException
     */
    // @ApiOperation(value="Bucket 의 폴더 삭제")
    @DeleteMapping(value = "/folder/{path}")
    public ResponseEntity<?> deleteFolder(@RequestParam("bucket") String bucket, @PathVariable String path) throws AwsS3RuntimeException {
        
        sampleFileService.deleteFolder(bucket, path.replaceAll("-", "/"));
        
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("resultMessage", messageSource.getMessage("common.process.complete"));
        
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
    
    /**
     * @Method      : getFiles
     * @Date        : 2021. 8. 19.
     * @author      : smlee1@kyobobook.com
     * @description : 특정 폴더 하위의 모든 파일을 조회 한다.
     * @param bucket - 조회 대상 Bucket
     * @param path - 조회 대상 폴더 경로(폴더의 구분은 '-' 으로 함)
     * @return
     * @throws AwsS3RuntimeException
     */
    // @ApiOperation(value="폴더에 속한 하위 파일 목록 조회")
    @GetMapping(value = {"/files", "/files/{path}"})
    public ResponseEntity<?> getFiles(@RequestParam("bucket") String bucket, @PathVariable Optional<String> path) throws AwsS3RuntimeException {
        
        String paramPath = "";
        if(path.isPresent()) {
            paramPath = path.get().replaceAll("-", "/");
        }
        
        return new ResponseEntity<>(sampleFileService.listObject(bucket, paramPath), HttpStatus.OK);
    }
    
    /**
     * @Method      : deleteFile
     * @Date        : 2021. 8. 19.
     * @author      : smlee1@kyobobook.com
     * @description : 파일을 삭제 한다.
     * @param fileName - 삭제 대상 파일명
     * @param bucket - 삭제 대상 Bucket
     * @param path - 삭제 대상 폴더 경로(폴더의 구분은 '-' 으로 함)
     * @return
     * @throws AwsS3RuntimeException
     */
    // @ApiOperation(value="파일 삭제")
    @DeleteMapping(value = "/files/{path}")
    public ResponseEntity<?> deleteFile(
            @RequestParam("fileName") String fileName
            , @RequestParam("bucket") String bucket
            , @PathVariable String path) throws AwsS3RuntimeException {
        
        logger.debug("//// path > " + path);
        logger.debug("//// fileName > " + fileName);
        
        String paramPath = path.replaceAll("-", "/") + "/" + fileName;
        sampleFileService.deleteFile(bucket, paramPath);
        
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("resultMessage", messageSource.getMessage("common.process.complete"));
        
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
    
    /**
     * @Method      : downloadFile
     * @Date        : 2021. 9. 10.
     * @author      : smlee1@kyobobook.com
     * @description : 파일을 다운로드 한다.
     * @param request - {@link HttpServletRequest}
     * @param response - {@link HttpServletResponse}
     * @param bucket - 파일이 위치해 있는 Bucket
     * @param fileName - 다운로드 파일 명
     * @param path - 다운로드 대상 파일의 경로
     * @return
     * @throws AwsS3RuntimeException
     */
    // @ApiOperation(value="파일 다운로드")
    @GetMapping(value = {"/download", "/download/{path}"})
    public ResponseEntity<?> downloadFile(HttpServletRequest request
            , HttpServletResponse response
            , @RequestParam("bucket") String bucket
            , @RequestParam("fileName") String fileName
            , @PathVariable Optional<String> path) throws AwsS3RuntimeException {
        
        logger.debug("//// path > " + path);
        logger.debug("//// fileName > " + fileName);
        String paramPath = "";
        if(path.isPresent()) {
            paramPath = path.get().replaceAll("-", "/");
        }
        
        sampleFileService.downloadFile(request, response, bucket, paramPath, fileName);
        
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("resultMessage", messageSource.getMessage("common.process.complete"));
        
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
