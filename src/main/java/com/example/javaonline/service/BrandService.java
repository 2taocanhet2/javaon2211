package com.example.javaonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.javaonline.entities.Brand;
import com.example.javaonline.repository.BrandRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BrandService {
	public static final int BRANDS_PER_PAGE = 10;

	@Autowired
	private BrandRepository repo;

	public List<Brand> listAll() {
		return (List<Brand>) repo.findAll();
	}


	public Brand save(Brand brand) {
		return repo.save(brand);
	}

	public Brand get(Integer id) throws Exception {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new Exception("Could not find any brand with ID " + id);
		}
	}

	public void delete(Integer id) throws Exception {
		Long countById = repo.countById(id);

		if (countById == null || countById == 0) {
			throw new Exception("Could not find any brand with ID " + id);
		}

		repo.deleteById(id);
	}

	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Brand brandByName = repo.findByName(name);

		if (isCreatingNew) {
			if (brandByName != null) return "Duplicate";
		} else {
			if (brandByName != null && brandByName.getId() != id) {
				return "Duplicate";
			}
		}

		return "OK";
	}
}
