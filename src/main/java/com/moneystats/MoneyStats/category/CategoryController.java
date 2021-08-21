package com.moneystats.MoneyStats.category;

import com.moneystats.MoneyStats.model.Category;
import com.moneystats.MoneyStats.repositoryCRUD.ICategoryCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    ICategoryCRUD categoryGEST;

    @GetMapping("/list")
    public List<Category> categoryList() {
        return categoryGEST.findAll();
    }
}
