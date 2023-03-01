package com.enjajiste.platform.controlers;

import com.enjajiste.platform.utils.FileHandler;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class TestController {

    @Autowired
    private final FileHandler fileHandler;

    @PostMapping("/file/upload")
    private Object upload(@RequestParam("file")MultipartFile file, @RequestParam("test") String test) {
        Map<String,Object> response = new HashMap<>();
        try {
            String savedFileName = fileHandler.saveFile(file);
            response.put("error", false);
            response.put("message", "filesaved");
            log.info("File saved under the name : {}", savedFileName);

            Files.deleteIfExists(Paths.get("uploads/" + savedFileName));
            log.info("deleted");
        }
        catch (IOException e) {
            response.put("error", true);
            response.put("message", "Error while saving the file");
//            e.printStackTrace();
        }
        return response;
    }
}
