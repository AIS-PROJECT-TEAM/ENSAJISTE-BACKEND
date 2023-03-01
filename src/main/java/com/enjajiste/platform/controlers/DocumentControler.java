package com.enjajiste.platform.controlers;

import com.enjajiste.platform.models.Document;
import com.enjajiste.platform.models.User;
import com.enjajiste.platform.repositories.DocumentRepository;
import com.enjajiste.platform.repositories.UserRepository;
import com.enjajiste.platform.services.UserService;
import com.enjajiste.platform.utils.AuthenticatedUser;
import com.enjajiste.platform.utils.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class DocumentControler {
    private final UserService userService;
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final FileHandler fileHandler;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/documents")
    public Object save(@RequestParam("file") MultipartFile file,@RequestBody Document document){
        Map<String,Object> response = new HashMap<>();
        try {
            AuthenticatedUser authenticatedUser =new AuthenticatedUser();
            User user=authenticatedUser.getUser(userService);
            document.setUser(user);
            String savedFileName = fileHandler.saveFile(file);
            document.setUrl(savedFileName);
            response.put("error", false);
            response.put("message", "filesaved");
        }
        catch (IOException e) {
            response.put("error", true);
            response.put("message", "Error while saving the file");
          e.printStackTrace();
        }
        document.setUser(userRepository.findById(document.getUser().getId()));
        documentRepository.save(document);
        return document;
    }

    @GetMapping("/documents/{offset}/{pageSize}")
    public Page<Document> findAll(@PathVariable int offset,@PathVariable int pageSize){
        Page<Document> documents = documentRepository.findAll(PageRequest.of(offset, pageSize));
        return documents;
    }

    @DeleteMapping(value = "/documents/{id}")
    public void delete(@PathVariable(required = true) int id) {
        System.out.println("id = "+id);
        Document document = documentRepository.findById((id));
        documentRepository.delete(document);
    }
}
