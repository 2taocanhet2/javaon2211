package com.example.javaonline.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brands")
@Data
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 45, unique = true)
	private String name;

	@Column(nullable = false, length = 128)
	private String logo;

	@ManyToMany
	@JoinTable(
			name = "brands_categories",
			joinColumns = @JoinColumn(name = "brand_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id")
			)
	private Set<Category> categories = new HashSet<>();

	public Brand() {

	}

	public Brand(String name) {
		this.name = name;
		this.logo = "brand-logo.png";
	}

	public Brand(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + ", categories="  + "]";
	}

	@Transient
	public String getLogoPath() {
		if (this.id == null) return "/image/image-thumbnail.png";

		return "/product-images/brand-logos/" + this.id + "/" + this.logo;
	}
}
