package com.example.javaonline.controller.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.javaonline.modal.Author;
import com.example.javaonline.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("rest/author")
public class AuthorRestController {
    @Autowired AuthorService authorService;

    @GetMapping("list")
    public List<Author> getAuthorList() {
        return authorService.getAuthorList();
    }

    @GetMapping("get/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping("/add")
    public Author createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    @PutMapping("/update/{id}")
    public Author updateAuthorById(@PathVariable Long id, @RequestBody Author author) {
        return authorService.updateAuthorById(id, author);
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deleteAuthorById(@PathVariable Long id, @RequestBody Author author) {
        return authorService.deleteAuthorById(id, author);
    }
}
