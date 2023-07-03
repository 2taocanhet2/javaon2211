package com.example.javaonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.javaonline.modal.Author;
import com.example.javaonline.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public List<Author> getAuthorList() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) {
        Author a = authorRepository.findById(id).get();
        return null;
    }

    public Author createAuthor(Author author) {
        authorRepository.save(author);
        return author;
    }

    public Author updateAuthorById(@PathVariable Long id, @RequestBody Author author) {
        return author;
    }

    public Boolean deleteAuthorById(@PathVariable Long id, @RequestBody Author author) {
        return false;
    }
}
