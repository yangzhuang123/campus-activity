package com.controller;

import com.annotation.IgnoreAuth;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 上传文件访问控制器
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    /**
     * 访问上传的文件
     */
    @IgnoreAuth
    @RequestMapping("/{filename}")
    public ResponseEntity<Resource> getUploadFile(@PathVariable String filename) {
        try {
            // 获取项目根目录
            String projectPath = System.getProperty("user.dir");
            File uploadPath = new File(projectPath, "upload");
            File file = new File(uploadPath, filename);
            
            // 检查文件是否存在
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            
            // 读取文件
            Resource resource = new FileSystemResource(file);
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
