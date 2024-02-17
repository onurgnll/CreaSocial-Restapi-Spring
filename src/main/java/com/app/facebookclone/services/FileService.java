package com.app.facebookclone.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    public void savePostImage(MultipartFile file , int postId, String filename) throws IOException {
        Path profile = Files.createDirectories(Paths.get("uploads/post_images/" + postId));

        try {
            Files.copy(file.getInputStream(), profile.resolve(filename));

        }catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                Files.delete(profile.resolve(filename));
                Files.copy(file.getInputStream(), profile.resolve(filename));
            }

        }
    }


    public void saveCoverImage(MultipartFile file , int userId) throws IOException {
        Path profile = Files.createDirectories(Paths.get("uploads/profile_images/" + userId));

        try {
            Files.copy(file.getInputStream(), profile.resolve("cover.png"));

        }catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                Files.delete(profile.resolve(file.getOriginalFilename()));
                Files.copy(file.getInputStream(), profile.resolve("cover.png"));
            }

        }
    }


    public Resource getCoverImage(int userId) {
        try {
            Path file = Paths.get("uploads/profile_images/" + userId).resolve("cover.png");
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void saveProfileImage(MultipartFile file , String userId) throws IOException {
        Path profile = Files.createDirectories(Paths.get("uploads/profile_images/" + userId));

        try {

            Files.copy(file.getInputStream(), profile.resolve("profile.png"));

        }catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                Files.delete(profile.resolve(file.getOriginalFilename()));
                Files.copy(file.getInputStream(), profile.resolve("profile.png"));
            }

        }
    }


    public Resource getProfileImage(int userId) {
        try {
            Path file = Paths.get("uploads/profile_images/" + userId).resolve("profile.png");
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

}
