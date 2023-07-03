package com.example.javaonline.sercurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // Cấu hình đường dẫn chặn
                .authorizeHttpRequests((authz) -> authz
                        // Những đường dân nào chưa backend thì phải đăng nhập
                        .antMatchers("/backend/user/**").hasAnyRole("ADMIN")
                        .antMatchers("/backend/**").authenticated()

                                // Ngoài nhưng request trên thì cho hép vượt qua
                                .anyRequest().permitAll()
                )

                // Cấu hình trang đăng nhập
                .formLogin((form) ->
                        form.loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/doLogin")
                        .defaultSuccessUrl("/backend/products/new")
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
            .logout((logout) -> logout.logoutUrl("/logout").permitAll())
                ;
        return http.build();
    }
}
