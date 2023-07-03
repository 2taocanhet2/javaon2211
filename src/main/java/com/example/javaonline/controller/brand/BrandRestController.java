package com.example.javaonline.controller.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import com.example.javaonline.dto.CategoryDTO;
import com.example.javaonline.entities.Brand;
import com.example.javaonline.entities.Category;
import com.example.javaonline.service.BrandService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("backend/brands/")
public class BrandRestController {
	@Autowired
	private BrandService service;

	@PostMapping("/check_unique")
	public String checkUnique(@Param("id") Integer id, @Param("name") String name) {
		return service.checkUnique(id, name);
	}

	@GetMapping("/{id}/categories")
	public List<CategoryDTO> listCategoriesByBrand(@PathVariable(name = "id") Integer brandId) throws Exception {
		List<CategoryDTO> listCategories = new ArrayList<>();

		try {
			Brand brand = service.get(brandId);
			Set<Category> categories = brand.getCategories();

			for (Category category : categories) {
				CategoryDTO dto = new CategoryDTO(category.getId(), category.getName());
				listCategories.add(dto);
			}

			return listCategories;
		} catch (Exception e) {
			throw new Exception();
		}
	}
}
