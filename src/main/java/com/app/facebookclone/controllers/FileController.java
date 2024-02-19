package com.app.facebookclone.controllers;

import com.app.facebookclone.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping("/post")
    public ResponseEntity uploadPostFile(@RequestParam("file") MultipartFile[] file, @RequestParam int postId) {
        try {

            for (int i  = 0 ; i < file.length ; i ++){
                fileService.savePostImage(file[i], postId , String.valueOf(i) + ".png");

            }

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @GetMapping("/post/{postId}/{imageId}")
    @ResponseBody
    public ResponseEntity<Resource> getPostImage(@PathVariable int postId, @PathVariable int imageId) {
        Resource file = fileService.getPostImage(postId , imageId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @PostMapping("/cover")
    public ResponseEntity uploadCoverFile(@RequestParam("file") MultipartFile file, @RequestParam int userId) {
        try {
            fileService.saveCoverImage(file, userId);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.OK);
        }
    }


    @GetMapping("/cover/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getCoverFile(@PathVariable String filename) {
        Resource file = fileService.getCoverImage(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @PostMapping("/profile")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, @RequestParam int userId) {
        try {
            fileService.saveProfileImage(file, userId);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.OK);
        }
    }


    @GetMapping("/profile/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileService.getProfileImage(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
