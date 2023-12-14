package com.enjajiste.platform.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileHandler {
    private final Path root = Paths.get("uploads");


    public String saveFile(MultipartFile file) throws IOException {
        String filename = getCustomefileName(file);
        Files.copy(file.getInputStream(), this.root.resolve(filename));
        return filename;
    }

    private String getCustomefileName(MultipartFile file){
        // Clening the path
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        //remove spaces and make lowercase
        filename = filename.toLowerCase().replaceAll(" ", "-");
        // Splitting by .
        String [] splitted = filename.split("\\.");
        // if the filename is correct => generate a unique filename
        if(splitted.length == 2){
            return splitted[0] + "-" + UUID.randomUUID().toString() + "." + splitted[1];
        }

        // TODO add exception to handle when the file name is not correct
        return null;
    }

    public void deleteFile(String filename) throws IOException {
        Files.deleteIfExists(Paths.get("uploads/" + filename));
    }


}
