package com.app.facebookclone.controllers;

import com.app.facebookclone.services.FileService;
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
    public ResponseEntity uploadPostFile(@RequestParam("file") MultipartFile[] file, @RequestParam int userId) {
        try {

            for (int i  = 0 ; i < file.length ; i ++){
                fileService.savePostImage(file[i], userId , String.valueOf(i) + ".png");

            }

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @GetMapping("/post/{postId}")
    @ResponseBody
    public ResponseEntity<Resource> getPostImage(@PathVariable int postId) {
        Resource file = fileService.getProfileImage(userId);
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


    @GetMapping("/cover/{userId}")
    @ResponseBody
    public ResponseEntity<Resource> getCoverFile(@PathVariable int userId) {
        Resource file = fileService.getCoverImage(userId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @PostMapping("/profile")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String userId) {
        try {
            fileService.saveProfileImage(file, userId);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.OK);
        }
    }


    @GetMapping("/profile/{userId}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable int userId) {
        Resource file = fileService.getProfileImage(userId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
