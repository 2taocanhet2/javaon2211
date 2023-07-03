package com.example.javaonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.javaonline.modal.Author;
import com.example.javaonline.modal.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
