package com.example.javaonline.service.user;//package t3h.vn.java2211on.service.user;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.stereotype.Service;
//import t3h.vn.java2211on.dto.Response;
//import t3h.vn.java2211on.dto.UserDto;
//import t3h.vn.java2211on.entities.UserEntity;
//import t3h.vn.java2211on.repository.UserRepository;
//import t3h.vn.java2211on.utils.Constant;
//
//@Service// 1 BEAN
//// Dùng cho T3H cơ sở 2
//@ConditionalOnProperty(prefix = "t3h", name = "service", havingValue = "2", matchIfMissing = false)
//public class UserServiceImpl_2 implements UserService {
//    @Autowired
//    UserRepository userRepository;
//
//    //    @Autowired
////    AuthorService authorService;
//    @Override
//    public void info() {
//        System.out.println("Cơ sơ 2 ===============");
//    }
//
//    @Override
//    public Response saveOrUpdate(UserDto userDto) {
//        if (userDto == null)
//            return Response.builder()
//                    .code(Constant.ERROR_CODE)
//                    .build();
//        if (userDto.getEmail() == null)
//            return Response.builder()
//                    .code(Constant.ERROR_CODE)
//                    .message("Email bắt buộc phải có thông tin!")
//                    .build();
//        if (userDto.getFileAvatar() != null) {
//            //@TODO Lưu file ảnh
//        }
//        UserEntity userEntity = null;
//        if (userDto.getId() != null) {
//            userEntity = userRepository.findById(userDto.getId()).orElse(null);
//        } else {
//            userEntity = new UserEntity();
//        }
//        // Đưa thông tin tư DTO sang entity
//        userEntity.setAddress(userDto.getAddress());
//        userEntity.setEmail(userDto.getEmail());
//        userEntity.setRole(userDto.getRole());
//        userEntity.setStatus(userDto.getStatus() == null ? 0 : userDto.getStatus());
//        userEntity.setPassword(userDto.getPassword());
//        userRepository.save(userEntity);
//        return Response.builder()
//                .code(Constant.OK_CODE)
//                .message("Tạo tài khoản công!")
//                .data(userDto)
//                .build();
//    }
//}
