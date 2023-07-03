package com.example.javaonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.javaonline.entities.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    void deleteAllByProductId(Integer productId);
}
