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
package kyobobook.common;

import org.apache.commons.lang3.StringUtils;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : PathParser.java
 * @Date        : 2021. 8. 18.
 * @author      : smlee1@kyobobook.com
 * @description : File 이나 Folder 경로를 실제 경로로 치환
 */
public final class PathParser {

    /**
     * @Method      : parseFolderPath
     * @Date        : 2021. 8. 18.
     * @author      : smlee1@kyobobook.com
     * @description : 
     * @param folderPath
     * @return
     */
    public static String parseFolderPath(String folderPath) {
        if (StringUtils.isBlank(folderPath)) {
            return ""; 
        }
            
        if ("/".equals(folderPath.trim())) {
            return ""; 
        }
        
        if (!StringUtils.endsWith(folderPath, "/")) {
            folderPath = folderPath + "/"; 
        }
        
        if (folderPath.contains("\\\\")) {
            folderPath = folderPath.replace("\\\\", "/");
        }
        
        if (folderPath.contains("\\")) {
            folderPath = folderPath.replace("\\", "/"); 
        }
        
        if (folderPath.contains("//")) {
            folderPath = folderPath.replace("//", "/");
        }
        
        return folderPath;
    }
      
    public static String parseFilePath(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return ""; 
        }
          
        if ("/".equals(filePath.trim())) {
            return ""; 
        }
          
        if (filePath.contains("\\\\")) {
            filePath = filePath.replace("\\\\", "/"); 
        }
        
        if (filePath.contains("\\")) {
            filePath = filePath.replace("\\", "/"); 
        }
        
        if (filePath.contains("//")) {
            filePath = filePath.replace("//", "/"); 
        }
        
        if (StringUtils.startsWith(filePath, "/")) {
            filePath = filePath.substring(1); 
        }
        
        if (StringUtils.endsWith(filePath, "/")) {
            filePath = filePath.substring(0, filePath.length() - 1);
        }
        
        return filePath;
    }
    
    public static String concatPath(String filePath, String fileName) {
        return parseFolderPath(filePath) + parseFolderPath(fileName); 
    }
}
