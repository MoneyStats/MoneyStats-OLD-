package com.moneystats.MoneyStats.category;

import com.moneystats.MoneyStats.category.entity.CategoryEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Inject
    ICategoryDAO categoryDAO;

    @GetMapping("/list")
    public List<CategoryEntity> categoryList() {
        return categoryDAO.findAll();
    }
}
