package com.app.facebookclone.services;

import com.app.facebookclone.entities.PersonalStatements;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private int serverPort;

    @Value("${development}")
    private boolean development;

    private PersonalStatementsService personalStatementsService;

    public FileService(PersonalStatementsService personalStatementsService) {
        this.personalStatementsService = personalStatementsService;
    }

    public void savePostImage(MultipartFile file, int postId, String filename) throws IOException {
        Path profile = Files.createDirectories(Paths.get("uploads/post_images/" + postId));

        try {
            Files.copy(file.getInputStream(), profile.resolve(filename));

        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                Files.delete(profile.resolve(filename));
                Files.copy(file.getInputStream(), profile.resolve(filename));
            }

        }
    }

    public Resource getPostImage(int postId, int imageId) {
        try {
            Path file = Paths.get("uploads/post_images/" + postId).resolve(imageId + ".png");
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

    public void saveCoverImage(MultipartFile file, int userId) throws IOException {
        Path profile = Paths.get("uploads/cover_images");

        try {
            Files.copy(file.getInputStream(), profile.resolve(userId + ".png"));

        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                Files.delete(profile.resolve(userId + ".png"));
                Files.copy(file.getInputStream(), profile.resolve(userId + ".png"));
            }

        } finally {
            PersonalStatements personalStatements = personalStatementsService.findByUserId((long) userId);

            if (development) {
                personalStatements.setCoverImage("http://" + serverAddress + ":" +serverPort +"/api/file/cover/"+ userId + ".png");

            }else {
                personalStatements.setCoverImage("https://" + serverAddress + ":" +serverPort +"/api/file/cover/"+ userId + ".png");

            }

            personalStatementsService.save(personalStatements);
        }
    }


    public Resource getCoverImage(String filename) {
        try {
            Path file = Paths.get("uploads/cover_images").resolve(filename);
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

    public void saveProfileImage(MultipartFile file, int userId) throws IOException {
        Path profile = Paths.get("uploads/profile_images");

        try {

            Files.copy(file.getInputStream(), profile.resolve(userId + ".png"));

        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                Files.delete(profile.resolve(userId + ".png"));
                Files.copy(file.getInputStream(), profile.resolve(userId + ".png"));
            }

        } finally {
            PersonalStatements personalStatements = personalStatementsService.findByUserId((long) userId);

            if (development) {
                personalStatements.setProfileImage("http://" + serverAddress + ":" +serverPort +"/api/file/profile/"+ userId + ".png");

            }else {
                personalStatements.setProfileImage("https://" + serverAddress + ":" +serverPort +"/api/file/profile/"+ userId + ".png");

            }

            personalStatementsService.save(personalStatements);
        }
    }


    public Resource getProfileImage(String filename) {
        try {
            Path file = Paths.get("uploads/profile_images").resolve(filename);
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
