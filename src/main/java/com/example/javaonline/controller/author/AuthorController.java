package com.example.javaonline.controller.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.javaonline.modal.Author;
import com.example.javaonline.service.AuthorService;
import com.example.javaonline.service.user.UserService;
import com.example.javaonline.service.user.UserServiceImpl;

import java.util.List;

@Controller
@RequestMapping("author")
public class AuthorController {
    @Autowired AuthorService authorService;

//    UserService userService = new UserServiceImpl();
    @GetMapping("get/{id}")
    public String detail(@PathVariable Long id,
                         Model model// Truyền dư liệu từ controller xuống file html
    ) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("a", author);
        return "author/detail.html";
    }

    @GetMapping("list")
    public String getAuthorList(Model model) {
        List<Author> list = authorService.getAuthorList();
        model.addAttribute("l", list);
        return "author/list.html";
    }

    // Để tạo mới được tác giả cần 2 hàm
    //1: Load ra giao diện
    @GetMapping("create")
    public String create(Model model) {
        List<Author> list = authorService.getAuthorList();
        model.addAttribute("author", new Author());
        return "author/create.html";
    }

    // 2: Submit (gửi dư liệu lên từ form)
    @PostMapping(value = "/save")
    public String createAuthor(/*@ModelAttribute */Author author,
                               RedirectAttributes model) {
        authorService.createAuthor(author);
//        model.addAttribute("author", author);
        model.addAttribute("message", "Tạo mới " + author.getName() + " thành công!");
        return "redirect:/author/list";
    }
}
