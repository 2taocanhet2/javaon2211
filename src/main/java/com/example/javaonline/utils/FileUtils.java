package com.example.javaonline.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class FileUtils {
    @Value("${folder.image}")
    String folderImage;

    public  String saveFile(MultipartFile multipartFile) throws Exception {
        File fd = new File(folderImage);
        if (!fd.exists()) fd.mkdirs();
        String name = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File targetFile = new File(folderImage + name);
        InputStream initialStream = multipartFile.getInputStream();
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);
        try (OutputStream outStream = new FileOutputStream(targetFile)) {
            outStream.write(buffer);
        }
        return name;
    }
}
