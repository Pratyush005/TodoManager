package com.lcwd.todo.todomanager.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

@RestController
@RequestMapping("/file")
public class FileController {
    Logger logger = LoggerFactory.getLogger(FileController.class);
    @PostMapping("/single")
    public String uploadSingleFile(@RequestParam("image") MultipartFile file) {
        logger.info("Name : {}", file.getName());
        logger.info("Content Type : {}", file.getContentType());
        logger.info("Original file name : {}", file.getOriginalFilename());
        logger.info("File size : {}", file.getSize());
        return "File test";
    }

    @PostMapping("/multiple")
    public String uploadMultipleFile(@RequestParam("files") MultipartFile[] files) {
        Arrays.stream(files).forEach(file -> {
            logger.info("File name {}", file.getOriginalFilename());
            logger.info("File Type {}", file.getContentType());
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");

            //call service to upload files : and pass file object
        });
        return "Handling multiple files";
    }

    //serving image files in response
    @GetMapping("/serve-image")
    public void serveImage(HttpServletResponse response) {
        try {
            InputStream fileInputStream = new FileInputStream("images/download.png");
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            StreamUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
