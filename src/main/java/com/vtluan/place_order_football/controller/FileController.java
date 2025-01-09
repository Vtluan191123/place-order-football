package com.vtluan.place_order_football.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vtluan.place_order_football.service.FileService;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("file")
    public String postUpload(@RequestParam("folderName") String folderName,
            @RequestParam("file") MultipartFile multipartFile) throws IllegalStateException, IOException {

        String directoryToSave = this.fileService.createDirectory(folderName);
        String fileName = this.fileService.upload(directoryToSave, multipartFile);
        return fileName;
    }

    @PostMapping("files")
    public String postUploads(@RequestParam("folderName") String folderName,
            @RequestParam("files") MultipartFile[] multipartFile) throws IllegalStateException, IOException {

        String directoryToSave = this.fileService.createDirectory(folderName);
        String fileNames = this.fileService.uploads(directoryToSave, multipartFile);
        return fileNames;
    }

}
