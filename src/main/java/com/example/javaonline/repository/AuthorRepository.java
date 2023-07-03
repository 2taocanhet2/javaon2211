package com.example.javaonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.javaonline.modal.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
