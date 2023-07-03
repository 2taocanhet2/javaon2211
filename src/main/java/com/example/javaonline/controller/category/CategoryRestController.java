package com.example.javaonline.controller.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.javaonline.service.CategoryService;

@RestController
public class CategoryRestController {

	@Autowired
	private CategoryService service;

	@PostMapping("/backend/categories/check_unique")
	public String checkUnique(@Param("id") Integer id, @Param("name") String name,
                              @Param("alias") String alias) {
		return service.checkUnique(id, name, alias);
	}
}