package com.example.javaonline.controller.account;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.javaonline.dto.Response;
import com.example.javaonline.dto.UserDto;
import com.example.javaonline.entities.UserEntity;
import com.example.javaonline.service.user.UserService;
import com.example.javaonline.service.user.UserServiceImpl;

import javax.validation.Valid;
import java.awt.*;

@Controller
@RequestMapping("backend/user")
public class AccountController {
    @Autowired
    UserService userService;

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("list", userService.getAll());
        return "user/list.html";
    }
    // Để tạo mới được tác giả cần 2 hàm
    //1: Load ra giao diện
    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "user/create.html";
    }

    @GetMapping("edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        UserEntity userEntity = userService.detail(id);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);
        model.addAttribute("userDto", userDto);
        return "user/detail.html";
    }

    @PostMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateAuthor( UserDto userDto,
                               RedirectAttributes model, @PathVariable Long id) {
//        if (bindingResult.hasErrors()) {
//            return "user/detail.html";
//        }

        userDto.setId(id);
        Response response = userService.saveOrUpdate(userDto);
//        model.addAttribute("author", author);
        model.addAttribute("message", response.getMessage());
        return "redirect:/user/list";
    }

    // 2: Submit (gửi dư liệu lên từ form)
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createAuthor(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult,
                                                   RedirectAttributes model) {
        if (userDto.getPassword() != null &&
                !userDto.getPassword().equals(userDto.getRepassword())) {
            bindingResult.rejectValue("repassword", "error.userDto", "Mật khẩu không trùng khớp");
        }
        if (bindingResult.hasErrors()) {
            return "user/create.html";
        }

        Response response = userService.saveOrUpdate(userDto);
//        model.addAttribute("author", author);
        model.addAttribute("message", response.getMessage());
        return "redirect:/user/list";
    }
}
