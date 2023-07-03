package com.example.javaonline.controller.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.javaonline.controller.config.WebConfiguration;
import com.example.javaonline.entities.Brand;
import com.example.javaonline.entities.Category;
import com.example.javaonline.service.BrandService;
import com.example.javaonline.service.CategoryService;
import com.example.javaonline.utils.FileUploadUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/backend/brands/")
public class BrandController {
    private String defaultRedirectURL = "redirect:/backend/brands/page/1?sortField=name&sortDir=asc";
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String listFirstPage() {
        return defaultRedirectURL;
    }


    @GetMapping("/new")
    public String newBrand(Model model) {
        List<Category> listCategories = categoryService.findAll();

        model.addAttribute("listCategories", listCategories);
        model.addAttribute("brand", new Brand());
        model.addAttribute("pageTitle", "Create New Brand");

        return "brands/brand_form";
    }

    @PostMapping("/save")
    public String saveBrand(Brand brand, @RequestParam("fileImage") MultipartFile multipartFile,
                            RedirectAttributes ra) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            brand.setLogo(fileName);

            Brand savedBrand = brandService.save(brand);
            String uploadDir = WebConfiguration.imageFolderStatic +  "brand-logos" + File.separator + savedBrand.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        } else {
            brandService.save(brand);
        }

        ra.addFlashAttribute("message", "The brand has been saved successfully.");
        return defaultRedirectURL;
    }

    @GetMapping("/edit/{id}")
    public String editBrand(@PathVariable(name = "id") Integer id, Model model,
                            RedirectAttributes ra) {
        try {
            Brand brand = brandService.get(id);
            List<Category> listCategories = categoryService.findAll();

            model.addAttribute("brand", brand);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Edit Brand (ID: " + id + ")");

            return "brands/brand_form";
        } catch (Exception ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return defaultRedirectURL;
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBrand(@PathVariable(name = "id") Integer id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            brandService.delete(id);
            String brandDir = WebConfiguration.imageFolderStatic +  "brand-logos" + File.separator + id;
            FileUploadUtil.removeDir(brandDir);

            redirectAttributes.addFlashAttribute("message",
                    "The brand ID " + id + " has been deleted successfully");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return defaultRedirectURL;
    }
}
