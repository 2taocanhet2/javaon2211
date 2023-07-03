package com.example.javaonline.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.example.javaonline.dto.Response;
import com.example.javaonline.dto.UserDto;
import com.example.javaonline.entities.UserEntity;
import com.example.javaonline.repository.UserRepository;
import com.example.javaonline.utils.Constant;
import com.example.javaonline.utils.FileUtils;

import java.util.List;

@Service// 1 BEAN
// Dùng cho T3H cơ sở 1
@ConditionalOnProperty(prefix = "t3h", name = "service", havingValue = "1")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileUtils fileUtils;

//    @Autowired
//    AuthorService authorService;

    @Override
    public Response saveOrUpdate(UserDto userDto) {
        if (userDto == null)
            return Response.builder()
                    .code(Constant.ERROR_CODE)
                    .build();
        if (userDto.getEmail() == null)
            return Response.builder()
                    .code(Constant.ERROR_CODE)
                    .message("Email bắt buộc phải có thông tin!")
                    .build();
        if (userDto.getFileAvatar() != null && !StringUtils.isEmpty(userDto.getFileAvatar().getOriginalFilename())) {
            //@TODO Lưu file ảnh
            try {
                userDto.setAvatar(fileUtils.saveFile(userDto.getFileAvatar()));
            } catch (Exception e) {}
        }
        UserEntity userEntity = null;
        if (userDto.getId() != null) {
            userEntity = userRepository.findById(userDto.getId()).orElse(null);
        }  else {
            userEntity = new UserEntity();
            userEntity.setPassword(userDto.getPassword());
            userEntity.setEmail(userDto.getEmail());
        }
        // Đưa thông tin tư DTO sang entity
        userEntity.setAddress(userDto.getAddress());
        userEntity.setRole(userDto.getRole());
        userEntity.setStatus(userDto.getStatus() == null ? 0 : userDto.getStatus());
        userEntity.setAvatar(userDto.getAvatar());
        userRepository.save(userEntity);
        return Response.builder()
                .code(Constant.OK_CODE)
                .message("Tạo tài khoản công!")
                .data(userDto)
                .build();
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity detail(Long id) {
        return userRepository.findById(id).orElse(new UserEntity());
    }

    @Override
    public void info() {
        System.out.println("Cơ sơ 1 +++++++++++++");
    }
}
