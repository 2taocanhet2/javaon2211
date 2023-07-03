package com.example.javaonline.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data// Tự động generate hàm getter setter
@Entity(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "PAGE_NUMBER")
    private Integer pageNumber;

//    @Column(name = "AUTHOR_ID")
//    private Long authorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHOR_ID")
    @JsonIgnore
    Author author;
}
