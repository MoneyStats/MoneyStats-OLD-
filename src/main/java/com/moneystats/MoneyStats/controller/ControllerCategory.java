package com.moneystats.MoneyStats.controller;

import com.moneystats.MoneyStats.model.Category;
import com.moneystats.MoneyStats.repositoryCRUD.ICategoryCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ControllerCategory {

    @Autowired
    ICategoryCRUD categoryGEST;

    @GetMapping("/category")
    public List<Category> categoryList() {
        return categoryGEST.findAll();
    }
}
