package com.example.javaonline.controller.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.javaonline.controller.config.WebConfiguration;
import com.example.javaonline.entities.Category;
import com.example.javaonline.service.CategoryService;
import com.example.javaonline.utils.FileUploadUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/backend/categories/")
public class CategoryController {
	@Autowired
	private CategoryService service;

//	@GetMapping("/categories")
//	public String listFirstPage(@Param("sortDir") String sortDir, Model model) {
//		return listByPage(1, sortDir, null, model);
//	}

	@GetMapping("/new")
	public String newCategory(Model model) {
		List<Category> listCategories = service.findAll();

		model.addAttribute("category", new Category());
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("pageTitle", "Create New Category");

		return "/categories/category_form";
	}

	@PostMapping("/save")
	public String saveCategory(Category category,
                               @RequestParam("fileImage") MultipartFile multipartFile,
                               RedirectAttributes ra) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			category.setImage(fileName);

			Category savedCategory = service.save(category);
			String uploadDir = WebConfiguration.imageFolderStatic + "category-images" + File.separator + savedCategory.getId();

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			service.save(category);
		}

		ra.addFlashAttribute("message", "The category has been saved successfully.");
		return "redirect:/backend/categories";
	}

	@GetMapping("/edit/{id}")
	public String editCategory(@PathVariable(name = "id") Integer id, Model model,
                               RedirectAttributes ra) {
		try {
			Category category = service.get(id);
			List<Category> listCategories = service.findAll();

			model.addAttribute("category", category);
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("pageTitle", "Edit Category (ID: " + id + ")");

			return "/categories/category_form";
		} catch (Exception ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/categories";
		}
	}

	@GetMapping("/{id}/enabled/{status}")
	public String updateCategoryEnabledStatus(@PathVariable("id") Integer id,
                                              @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		service.updateCategoryEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The category ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/backend/categories";
	}

	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable(name = "id") Integer id,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
		try {
			service.delete(id);
			String categoryDir = WebConfiguration.imageFolderStatic + "category-images"+ File.separator + id;
			FileUploadUtil.removeDir(categoryDir);

			redirectAttributes.addFlashAttribute("message",
					"The category ID " + id + " has been deleted successfully");
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}

		return "redirect:/backend/categories";
	}

//	@GetMapping("/categories/export/csv")
//	public void exportToCSV(HttpServletResponse response) throws IOException {
//		List<Category> listCategories = service.findAll();
//		CategoryCsvExporter exporter = new CategoryCsvExporter();
//		exporter.export(listCategories, response);
//	}
}
