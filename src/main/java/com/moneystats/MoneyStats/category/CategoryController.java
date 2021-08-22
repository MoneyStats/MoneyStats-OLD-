package com.moneystats.MoneyStats.category;

import com.moneystats.MoneyStats.category.DTO.CategoryDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Inject CategoryService categoryService;

    @GetMapping("/list")
    public List<CategoryDTO> categoryList() throws CategoryException {
        return categoryService.categoryDTOList();
    }
}
