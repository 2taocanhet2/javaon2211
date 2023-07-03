package com.example.javaonline.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "USER")// tên bảng
@Data
public class UserEntity {
    @Id
    @Column(name = "ID")// tên cột
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto increment
    Long id;

    @Column(name = "EMAIL", unique = true)
    String email;

    @Column(name = "ADDRESS")
    String address;

    @Column(name = "ROLE")
    String role;

    @Column(name = "PASSWORD")
    String password;

    @Column(name = "AVATAR")
    String avatar;

    @Column(name = "STATUS")
    Integer status;
}
