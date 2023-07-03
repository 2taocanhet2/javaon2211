package com.example.javaonline.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "product_images")
@Data
public class ProductImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(name = "product_id")
	private Integer productId;


	public ProductImage() {
	}

	public ProductImage(Integer id, String name) {
		this.id = id;
		this.name = name;
	}


	public ProductImage(String name) {
		this.name = name;
	}


	@Transient
	public String getImagePath() {
		return "/product-images/" + productId + "/extras/" + this.name;
	}

}
