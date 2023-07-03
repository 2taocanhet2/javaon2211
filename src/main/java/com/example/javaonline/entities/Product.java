package com.example.javaonline.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "products")
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true, length = 256, nullable = false)
	private String name;

	@Column(unique = true, length = 256, nullable = false)
	private String alias;

	@Column(length = 512, nullable = false, name = "short_description")
	private String shortDescription;

	@Column(length = 4096, nullable = false, name = "full_description")
	private String fullDescription;

	@Column(name = "created_time")
	private Date createdTime;

	@Column(name = "updated_time")
	private Date updatedTime;

	private boolean enabled;

	@Column(name = "in_stock")
	private boolean inStock;

	private float cost;

	private float price;

	@Column(name = "discount_percent")
	private float discountPercent;

	private float length;
	private float width;
	private float height;
	private float weight;

	@Column(name = "main_image", nullable = false)
	private String mainImage;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@OneToMany
	@JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Set<ProductImage> images = new HashSet<>();

//	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<ProductDetail> details = new ArrayList<>();


	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + "]";
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public Set<ProductImage> getImages() {
		return images;
	}

	public void setImages(Set<ProductImage> images) {
		this.images = images;
	}

	public void addExtraImage(String imageName) {
		this.images.add(new ProductImage(imageName));
	}

	@Transient
	public String getMainImagePath() {
		if (id == null || mainImage == null) return "/image/image-thumbnail.png";

		return "/product-images/" + this.id + "/" + this.mainImage;
	}

//	public List<ProductDetail> getDetails() {
//		return details;
//	}
//
//	public void setDetails(List<ProductDetail> details) {
//		this.details = details;
//	}
//
//	public void addDetail(String name, String value) {
//		this.details.add(new ProductDetail(name, value, this));
//	}
//
//	public void addDetail(Integer id, String name, String value) {
//		this.details.add(new ProductDetail(id, name, value, this));
//	}

	public boolean containsImageName(String imageName) {
		Iterator<ProductImage> iterator = images.iterator();

		while (iterator.hasNext()) {
			ProductImage image = iterator.next();
			if (image.getName().equals(imageName)) {
				return true;
			}
		}

		return false;
	}

	@Transient
	public String getShortName() {
		if (name.length() > 70) {
			return name.substring(0, 70).concat("...");
		}
		return name;
	}

	@Transient
	public float getDiscountPrice() {
		if (discountPercent > 0) {
			return price * ((100 - discountPercent) / 100);
		}
		return this.price;
	}
}
