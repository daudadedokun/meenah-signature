package com.meenah.meenahsignature.category;

import com.meenah.meenahsignature.category.Category;
import com.meenah.meenahsignature.category.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("productmanagement/api/v1/category")
@AllArgsConstructor
public class CategoryApi {

    private final CategoryService categoryService;

    @GetMapping(path = "/all")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }
}
