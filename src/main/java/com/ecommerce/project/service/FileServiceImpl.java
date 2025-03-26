package com.ecommerce.project.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {


    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File name of original/current file
        String originalFileName= file.getOriginalFilename();

        //Generate a unique fileName
        String randomId= UUID.randomUUID().toString();
        //Mat.jpj randomId=1234 -->1234.jpj
        String fileName=randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath=path+ File.separator+fileName;

        //check if path exists or Create
        File folder=new File(path);
        if(!folder.exists())
            folder.mkdir();
        //Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        //return the file name
        return fileName;
    }
}
