package com.example.javaonline.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.javaonline.dto.ProductSaveHelper;
import com.example.javaonline.entities.Brand;
import com.example.javaonline.entities.Category;
import com.example.javaonline.entities.Product;
import com.example.javaonline.service.BrandService;
import com.example.javaonline.service.CategoryService;
import com.example.javaonline.service.ProductService;
import com.example.javaonline.utils.FileUploadUtil;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/backend/products/")
public class ProductController {
	private String defaultRedirectURL = "redirect:/backend/products/page/1?sortField=name&sortDir=asc&categoryId=0";
	@Autowired
    private ProductService productService;
	@Autowired
    private BrandService brandService;

	@Autowired
    private CategoryService categoryService;

	@GetMapping("/page/{pageNum}")
	public String listByPage(
//			@PagingAndSortingParam(listName = "listProducts", moduleURL = "/products") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("keyword") String keyword,
			@Param("categoryId") Integer categoryId
	) {

		productService.listByPage(pageNum, keyword, categoryId, model);

		List<Category> listCategories = categoryService.findAll();
		model.addAttribute("keyword", keyword);
		model.addAttribute("moduleURL", "/backend/products");

		if (categoryId != null) model.addAttribute("categoryId", categoryId);
		model.addAttribute("listCategories", listCategories);

		return "products/products";
	}

	@GetMapping("/")
	public String listFirstPage(Model model) {
		return defaultRedirectURL;
	}


	@GetMapping("/new")
	public String newProduct(Model model) {
		List<Brand> listBrands = brandService.listAll();

		Product product = new Product();
		product.setEnabled(true);
		product.setInStock(true);

		model.addAttribute("product", product);
		model.addAttribute("listBrands", listBrands);
		model.addAttribute("pageTitle", "Create New Product");
		model.addAttribute("numberOfExistingExtraImages", 0);

		return "products/product_form.html";
	}

	@PostMapping("/save")
		public String saveProduct(Product product, RedirectAttributes ra,
                              @RequestParam(value = "fileImage", required = false) MultipartFile mainImageMultipart,
                              @RequestParam(value = "extraImage", required = false) MultipartFile[] extraImageMultiparts,
                              @RequestParam(name = "detailIDs", required = false) String[] detailIDs,
                              @RequestParam(name = "detailNames", required = false) String[] detailNames,
                              @RequestParam(name = "detailValues", required = false) String[] detailValues,
                              @RequestParam(name = "imageIDs", required = false) String[] imageIDs,
                              @RequestParam(name = "imageNames", required = false) String[] imageNames
			) throws IOException {


		ProductSaveHelper.setMainImageName(mainImageMultipart, product);
		ProductSaveHelper.setExistingExtraImageNames(imageIDs, imageNames, product);
		ProductSaveHelper.setNewExtraImageNames(extraImageMultiparts, product);

		Product savedProduct = productService.save(product);

		ProductSaveHelper.saveUploadedImages(mainImageMultipart, extraImageMultiparts, savedProduct);

		ProductSaveHelper.deleteExtraImagesWeredRemovedOnForm(product);

		ra.addFlashAttribute("message", "The product has been saved successfully.");

		return defaultRedirectURL;
	}


	@GetMapping("/{id}/enabled/{status}")
	public String updateProductEnabledStatus(@PathVariable("id") Integer id,
                                             @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		productService.updateProductEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The Product ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return defaultRedirectURL;
	}

	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Integer id,
                                Model model, RedirectAttributes redirectAttributes) {
		try {
			productService.delete(id);
			String productExtraImagesDir = "../product-images/" + id + "/extras";
			String productImagesDir = "../product-images/" + id;

			FileUploadUtil.removeDir(productExtraImagesDir);
			FileUploadUtil.removeDir(productImagesDir);

			redirectAttributes.addFlashAttribute("message",
					"The product ID " + id + " has been deleted successfully");
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}

		return defaultRedirectURL;
	}

	@GetMapping("/edit/{id}")
	public String editProduct(@PathVariable("id") Integer id, Model model,
                              RedirectAttributes ra) {
		try {
			Product product = productService.get(id);
			List<Brand> listBrands = brandService.listAll();
			Integer numberOfExistingExtraImages = product.getImages().size();

			boolean isReadOnlyForSalesperson = false;


			model.addAttribute("isReadOnlyForSalesperson", isReadOnlyForSalesperson);
			model.addAttribute("product", product);
			model.addAttribute("listBrands", listBrands);
			model.addAttribute("pageTitle", "Edit Product (ID: " + id + ")");
			model.addAttribute("numberOfExistingExtraImages", numberOfExistingExtraImages);

			return "products/product_form.html";

		} catch (Exception e) {
			ra.addFlashAttribute("message", e.getMessage());

			return defaultRedirectURL;
		}
	}

	@GetMapping("/detail/{id}")
	public String viewProductDetails(@PathVariable("id") Integer id, Model model,
                                     RedirectAttributes ra) {
		try {
			Product product = productService.get(id);
			model.addAttribute("product", product);

			return "products/product_detail_modal";

		} catch (Exception e) {
			ra.addFlashAttribute("message", e.getMessage());

			return defaultRedirectURL;
		}
	}
}
