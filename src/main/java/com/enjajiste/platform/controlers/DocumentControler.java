package com.enjajiste.platform.controlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enjajiste.platform.models.Document;
import com.enjajiste.platform.models.User;
import com.enjajiste.platform.repositories.DocumentRepository;
import com.enjajiste.platform.repositories.UserRepository;
import com.enjajiste.platform.services.UserService;
import com.enjajiste.platform.utils.AuthenticatedUser;
import com.enjajiste.platform.utils.FileHandler;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class DocumentControler {
	private final UserService userService = null;
	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private UserRepository userRepository;

	private final FileHandler fileHandler;

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/documents")
	public Object save(@RequestParam("file") MultipartFile file, @RequestBody Document document) {
		Map<String, Object> response = new HashMap<>();
		try {
			AuthenticatedUser authenticatedUser = new AuthenticatedUser();
			User user = authenticatedUser.getUser(userService);
			document.setUser(user);
			String savedFileName = fileHandler.saveFile(file);
			document.setUrl(savedFileName);
			response.put("error", false);
			response.put("message", "filesaved");
		} catch (IOException e) {
			response.put("error", true);
			response.put("message", "Error while saving the file");
			e.printStackTrace();
		}
		document.setUser(userRepository.findById(document.getUser().getId()));
		documentRepository.save(document);
		return document;
	}

	@GetMapping("/documents/{offset}/{pageSize}")
	public Page<Document> findAll(@PathVariable int offset, @PathVariable int pageSize) {
		Page<Document> documents = documentRepository.findAll(PageRequest.of(offset, pageSize));
		return documents;
	}

	@DeleteMapping(value = "/documents/{id}")
	public void delete(@PathVariable(required = true) int id) {
		System.out.println("id = " + id);
		Document document = documentRepository.findById((id));
		documentRepository.delete(document);
	}
}
