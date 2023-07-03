package com.example.javaonline.sercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.javaonline.entities.UserEntity;
import com.example.javaonline.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class UserServiceDetailImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findFirstByEmail(username);
        if (userEntity == null) throw new UsernameNotFoundException("Không tồn tại tài khoản");



        return new User(username, userEntity.getPassword(), userEntity.getStatus() == 1,
                true, true, true,
                Arrays.asList(new SimpleGrantedAuthority[]{new SimpleGrantedAuthority("ROLE_" + userEntity.getRole())}));
    }
}
