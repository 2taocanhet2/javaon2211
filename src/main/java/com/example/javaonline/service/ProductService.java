package com.example.javaonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.example.javaonline.entities.Product;
import com.example.javaonline.entities.ProductImage;
import com.example.javaonline.repository.ProductImageRepository;
import com.example.javaonline.repository.ProductRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ProductService {
	public static final int PRODUCTS_PER_PAGE = 5;

	@Autowired
    private ProductRepository repo;

	@Autowired
	private ProductImageRepository productImageRepository;

	public List<Product> listAll() {
		return (List<Product>) repo.findAll();
	}

	public void listByPage(int pageNum, String keyword, Integer categoryId, Model model) {
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
		Page<Product> page = null;

		if (keyword != null && !keyword.isEmpty()) {
			if (categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
				page = repo.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
			} else {
				page = repo.findAll(keyword, pageable);
			}
		} else {
			if (categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
				page = repo.findAllInCategory(categoryId, categoryIdMatch, pageable);
			} else {
				page = repo.findAll(pageable);
			}
		}

		List<?> listItems = page.getContent();
		int pageSize = page.getSize();

		long startCount = (pageNum - 1) * pageSize + 1;
		long endCount = startCount + pageSize - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("list", listItems);
	}

	public Product save(Product product) {
		if (product.getId() == null) {
			product.setCreatedTime(new Date());
		} else {
			// xóa ảnh của sản phẩm khi cập nhật
			productImageRepository.deleteAllByProductId(product.getId());
		}

		if (product.getAlias() == null || product.getAlias().isEmpty()) {
			String defaultAlias = product.getName().replaceAll(" ", "-");
			product.setAlias(defaultAlias);
		} else {
			product.setAlias(product.getAlias().replaceAll(" ", "-"));
		}

		product.setUpdatedTime(new Date());

		Product save = repo.save(product);

		if(product.getImages() != null && product.getImages().size() > 0) {
			for (ProductImage productImage: product.getImages()
				 ) {
				productImage.setProductId(save.getId());
				productImageRepository.save(productImage);
			}
		}
		return save;

	}

	public void saveProductPrice(Product productInForm) {
		Product productInDB = repo.findById(productInForm.getId()).get();
		productInDB.setCost(productInForm.getCost());
		productInDB.setPrice(productInForm.getPrice());
		productInDB.setDiscountPercent(productInForm.getDiscountPercent());

		repo.save(productInDB);
	}

	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Product productByName = repo.findByName(name);

		if (isCreatingNew) {
			if (productByName != null) return "Duplicate";
		} else {
			if (productByName != null && productByName.getId() != id) {
				return "Duplicate";
			}
		}

		return "OK";
	}

	public void updateProductEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}

	public void delete(Integer id) throws Exception {
		Long countById = repo.countById(id);

		if (countById == null || countById == 0) {
			throw new Exception("Could not find any product with ID " + id);
		}

		repo.deleteById(id);
	}

	public Product get(Integer id) throws Exception {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new Exception("Could not find any product with ID " + id);
		}
	}

	/*public void listByPage(int pageNum, PagingAndSortingHelper helper, Integer categoryId) {
		Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE, pageNum);
		String keyword = helper.getKeyword();
		Page<Product> page = null;

		if (keyword != null && !keyword.isEmpty()) {
			if (categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
				page = repo.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
			} else {
				page = repo.findAll(keyword, pageable);
			}
		} else {
			if (categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
				page = repo.findAllInCategory(categoryId, categoryIdMatch, pageable);
			} else {
				page = repo.findAll(pageable);
			}
		}

		helper.updateModelAttributes(pageNum, page);
	}*/
}
