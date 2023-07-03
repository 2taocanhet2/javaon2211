package com.example.javaonline.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data// Tự động generate hàm getter setter
@Entity(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;
    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "GENDER")
    private Integer gender;

    @OneToMany( mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<Book> bookList;

//    public void setBookList(List<Book> bookList) {
//        if (!CollectionUtils.isEmpty(bookList)){
//            for (Book b: bookList
//                 ) {
//                b.setAuthorId(this.id);
//            }
//        }
//        this.bookList = bookList;
//    }
}
