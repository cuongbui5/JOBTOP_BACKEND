package com.example.jobs_top.service;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadFileService {
    private final Cloudinary cloudinary;

    public UploadFileService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadFile(MultipartFile multipartFile)  {
        try {
            return cloudinary.uploader()
                    .upload(multipartFile.getBytes(),
                            Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url")
                    .toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

}
