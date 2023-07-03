package com.example.javaonline.controller;

import org.springframework.web.bind.annotation.*;
import com.example.javaonline.modal.Author;

@RestController
public class HelloRestController {

    @GetMapping("get")// Phương thức để truy xuất thông tin
    public String get(@RequestParam String param){// xuất hiện sau dấu ? = > được sử dụng để tìm kiếm
//        Author a = Author.builder()
//                .address("Hà nội")
//                .build();
        return "get " + param;
    }

    @PostMapping("post")// được sử dụng khi tạo mới dữ liệu
    public String post(@RequestBody Object o){// che dấu thông tin
        return "post " + o.toString();
    }

    @PutMapping("put/{id}")// được sử dụng khi cập nhật dữ liệu
    public String put(@PathVariable String id){
        return "put " + id;
    }

    @DeleteMapping("delete/{id}")// được sử dụng khi muốn xóa thông tin
    public String delete(@PathVariable String id){
        return "delete " + id;
    }

//     Viết api thêm("/add" + request body),
//     sửa("/update" + request body) ,
//     xóa("/delete/{id}" + Pathvariable),
//     lấy thông tin chi tiết("get/{id}"  + Pathvariable),
//     danh sách tác giả ("list" )
}
