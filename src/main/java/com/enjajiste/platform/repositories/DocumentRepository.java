package com.enjajiste.platform.repositories;

import com.enjajiste.platform.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document,Integer> {
    Document findById(int id);
}
