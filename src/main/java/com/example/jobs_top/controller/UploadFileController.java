package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.UploadFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/upload-file")
public class UploadFileController {
    private final UploadFileService uploadFileService;

    public UploadFileController(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println(file);
        return ResponseEntity.ok()
                .body(new ApiResponse<>(
                        200,
                        "Upload file successfully",
                        uploadFileService.uploadFile(file)
                ));

    }
}
