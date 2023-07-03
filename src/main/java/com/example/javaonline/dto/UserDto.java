package com.example.javaonline.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserDto {
    Long id;
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Không đúng định dạng email")
    String email;
    @NotBlank(message = "Địa chỉ không được trống")
    String address;
    String role;
    @NotBlank(message = "Mật khẩu không được phép trống")
    @Min(value = 6, message = "Nhập ít nhất 6 ky tự")
    String password;
    String avatar;
    String repassword;
    Integer status;
    List<String> phones;
    MultipartFile fileAvatar;
}
