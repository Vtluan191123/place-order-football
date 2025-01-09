package com.vtluan.place_order_football.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${root_file_upload}")
    private String rootFileUpload;

    public String createDirectory(String folderName) {
        Path path = Paths.get(rootFileUpload + folderName);
        File tmpDir = new File(path.toString());

        if (!tmpDir.isDirectory()) {
            try {
                Files.createDirectories(path);
                System.out.println("create folder success");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("folder has exists");
        }
        return tmpDir.getAbsolutePath();
    }

    public String upload(String directoryToSave, MultipartFile file) throws IllegalStateException, IOException {
        String nameFile = System.currentTimeMillis() + "-" + file.getOriginalFilename();

        File fileUpload = new File(directoryToSave + "/" + nameFile);

        try {
            file.transferTo(fileUpload);
        } catch (IOFileUploadException e) {
            throw e;
        }
        return nameFile;
    }

    public void deleteFile(String pathFIle) {
        File file = new File(pathFIle);
        if (file.exists()) {
            file.delete();
            System.out.println("File đã được xóa");
        }
    }

    // upload a lot of files

    public String uploads(String directoryToSave, MultipartFile[] file) throws IllegalStateException, IOException {

        if (file == null) {
            return "";
        }
        List<String> nameFiles = new ArrayList<>();
        for (int i = 0; i < file.length; i++) {
            String nameFile = System.currentTimeMillis() + "-" + file[i].getOriginalFilename();
            nameFiles.add(nameFile);
            File fileUpload = new File(directoryToSave + "/" + nameFile);

            try {
                file[i].transferTo(fileUpload);
            } catch (IOFileUploadException e) {
                throw e;
            }
        }

        return String.join(",", nameFiles);
    }

}
